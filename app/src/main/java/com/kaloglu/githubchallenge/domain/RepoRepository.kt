/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kaloglu.githubchallenge.domain

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.kaloglu.githubchallenge.viewobjects.Contributor
import com.kaloglu.githubchallenge.viewobjects.Repo
import com.kaloglu.githubchallenge.viewobjects.Resource
import com.kaloglu.githubchallenge.injection.scopes.PerApplication
import com.kaloglu.githubchallenge.api.ApiResponse
import com.kaloglu.githubchallenge.api.ApiSuccessResponse
import com.kaloglu.githubchallenge.api.GithubService
import com.kaloglu.githubchallenge.api.RepoSearchResponse
import com.kaloglu.githubchallenge.data.cache.GithubDb
import com.kaloglu.githubchallenge.data.cache.RepoDao
import com.kaloglu.githubchallenge.utils.AbsentLiveData
import com.kaloglu.githubchallenge.utils.RateLimiter
import com.kaloglu.githubchallenge.viewobjects.RepoSearchResult
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Repository that handles Repo instances.
 *
 * unfortunate naming :/ .
 * Repo - value object name
 * Repository - type of this class.
 */
@PerApplication
class RepoRepository @Inject constructor(
        private val appExecutors: AppExecutors,
        private val db: GithubDb,
        private val repoDao: RepoDao,
        private val githubService: GithubService
) {

    private val repoListRateLimit = RateLimiter<String>(10, TimeUnit.MINUTES)

    fun loadRepos(owner: String): LiveData<Resource<List<Repo>>> {
        return object : NetworkBoundResource<List<Repo>, List<Repo>>(appExecutors) {
            override fun saveCallResult(item: List<Repo>) {
                repoDao.insertRepos(item)
            }

            override fun shouldFetch(data: List<Repo>?): Boolean {
                return data == null || data.isEmpty() || repoListRateLimit.shouldFetch(owner)
            }

            override fun loadFromDb(): LiveData<List<Repo>> = MutableLiveData<List<Repo>>() //repoDao.loadRepositories(owner)

            override fun createCall() = githubService.getRepos(owner)

            override fun onFetchFailed() {
                repoListRateLimit.reset(owner)
            }
        }.asLiveData()
    }

    fun loadRepo(owner: String, name: String): LiveData<Resource<Repo>> {
        return object : NetworkBoundResource<Repo, Repo>(appExecutors) {
            override fun saveCallResult(item: Repo) {
                repoDao.insert(item)
            }

            override fun shouldFetch(data: Repo?) = data == null

            override fun loadFromDb() = repoDao.load(
                    ownerLogin = owner,
                    name = name
            )

            override fun createCall() = githubService.getRepo(
                    owner = owner,
                    name = name
            )
        }.asLiveData()
    }

    fun loadContributors(owner: String, name: String): LiveData<Resource<List<Contributor>>> {
        return object : NetworkBoundResource<List<Contributor>, List<Contributor>>(appExecutors) {
            override fun saveCallResult(item: List<Contributor>) {
                item.forEach {
                    it.repoName = name
                    it.repoOwner = owner
                }
                db.runInTransaction {
                    repoDao.createRepoIfNotExists(
                            Repo(
                                    id = Repo.UNKNOWN_ID,
                                    name = name,
                                    fullName = "$owner/$name",
                                    description = "",
                                    owner = Repo.Owner(owner, null),
                                    stars = 0
                            )
                    )
                    repoDao.insertContributors(item)
                }
            }

            override fun shouldFetch(data: List<Contributor>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb() = repoDao.loadContributors(owner, name)

            override fun createCall() = githubService.getContributors(owner, name)
        }.asLiveData()
    }

    fun searchNextPage(query: String): LiveData<Resource<Boolean>> {
        val fetchNextSearchPageTask = FetchNextSearchPageTask(
                query = query,
                githubService = githubService,
                db = db
        )
        appExecutors.networkIO().execute(fetchNextSearchPageTask)
        return fetchNextSearchPageTask.liveData
    }

    fun search(query: String): LiveData<Resource<List<Repo>>> {
        return object : NetworkBoundResource<List<Repo>, RepoSearchResponse>(appExecutors) {

            override fun saveCallResult(item: RepoSearchResponse) {
                val repoIds = item.items.map { it.id }
                val repoSearchResult = RepoSearchResult(
                        query = query,
                        repoIds = repoIds,
                        totalCount = item.total,
                        next = item.nextPage
                )
                db.beginTransaction()
                try {
                    repoDao.insertRepos(item.items)
                    repoDao.insert(repoSearchResult)
                    db.setTransactionSuccessful()
                } finally {
                    db.endTransaction()
                }
            }

            override fun shouldFetch(data: List<Repo>?) = data == null

            override fun loadFromDb(): LiveData<List<Repo>> {
                return Transformations.switchMap(repoDao.search(query)) { searchData ->
                    if (searchData == null) {
                        AbsentLiveData.create()
                    } else {
                        repoDao.loadOrdered(searchData.repoIds)
                    }
                }
            }

            override fun createCall(): LiveData<ApiResponse<RepoSearchResponse>> {
                return githubService.searchRepos(query)
            }

            override fun processResponse(response: ApiSuccessResponse<RepoSearchResponse>)
                    : RepoSearchResponse {
                val body = response.body
                body.nextPage = response.nextPage
                return body
            }
        }.asLiveData()
    }
}

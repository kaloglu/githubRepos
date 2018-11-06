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

package com.kaloglu.githubchallenge.domain.repo

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import com.kaloglu.githubchallenge.viewobjects.Repo
import com.kaloglu.githubchallenge.viewobjects.Resource
import com.kaloglu.githubchallenge.injection.scopes.PerApplication
import com.kaloglu.githubchallenge.api.ApiResponse
import com.kaloglu.githubchallenge.api.ApiSuccessResponse
import com.kaloglu.githubchallenge.api.GithubService
import com.kaloglu.githubchallenge.api.RepoSearchResponse
import com.kaloglu.githubchallenge.data.cache.GithubDb
import com.kaloglu.githubchallenge.data.cache.RepoDao
import com.kaloglu.githubchallenge.domain.AppExecutors
import com.kaloglu.githubchallenge.domain.FetchNextSearchPageTask
import com.kaloglu.githubchallenge.domain.NetworkBoundResource
import com.kaloglu.githubchallenge.utils.AbsentLiveData
import com.kaloglu.githubchallenge.utils.RateLimiter
import com.kaloglu.githubchallenge.viewobjects.RepoSearchResult
import com.kaloglu.githubchallenge.viewobjects.UserRepo
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

    fun loadRepo(owner: String, name: String) = LoadRepo(owner, name).asLiveData()

    fun loadRepos(owner: String) = LoadRepos(owner).asLiveData()

    fun search(query: String) = Search(query).asLiveData()

    fun searchNextPage(query: String): LiveData<Resource<Boolean>> {
        val fetchNextSearchPageTask = FetchNextSearchPageTask(
                query = query,
                githubService = githubService,
                db = db
        )
        appExecutors.networkIO().execute(fetchNextSearchPageTask)
        return fetchNextSearchPageTask.liveData
    }

    inner class LoadRepo(private val owner: String, private val name: String)
        : NetworkBoundResource<Repo, Repo>(appExecutors) {
        override fun saveCallResult(item: Repo) = repoDao.insert(item)

        override fun shouldFetch(data: Repo?) = data == null

        override fun loadFromDb() = repoDao.load(owner, name)

        override fun createCall() = githubService.getRepo(owner, name)
    }

    inner class LoadRepos(private val owner: String)
        : NetworkBoundResource<List<UserRepo>, List<UserRepo>>(appExecutors) {

        override fun saveCallResult(item: List<UserRepo>) = Unit

        override fun shouldFetch(data: List<UserRepo>?) = true

        override fun loadFromDb(): LiveData<List<UserRepo>> = repoDao.loadRepositories(owner)

        override fun createCall(): LiveData<ApiResponse<List<UserRepo>>> = githubService.getRepos(owner)

        override fun onFetchFailed() = Unit
    }

    inner class Search(private val query: String)
        : NetworkBoundResource<List<Repo>, RepoSearchResponse>(appExecutors) {

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

        override fun loadFromDb() =
                Transformations.switchMap(repoDao.search(query)) {
                    when (it) {
                        null -> AbsentLiveData.create()
                        else -> repoDao.loadOrdered(it.repoIds)
                    }
                }

        override fun createCall(): LiveData<ApiResponse<RepoSearchResponse>> =
                githubService.searchRepos(query)

        override fun processResponse(response: ApiSuccessResponse<RepoSearchResponse>)
                : RepoSearchResponse {
            val body = response.body
            body.nextPage = response.nextPage
            return body
        }
    }
}

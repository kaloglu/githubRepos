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
import com.kaloglu.githubchallenge.viewobjects.Resource
import com.kaloglu.githubchallenge.api.ApiEmptyResponse
import com.kaloglu.githubchallenge.api.ApiErrorResponse
import com.kaloglu.githubchallenge.api.ApiResponse
import com.kaloglu.githubchallenge.api.ApiSuccessResponse
import com.kaloglu.githubchallenge.api.GithubService
import com.kaloglu.githubchallenge.data.cache.GithubDb
import com.kaloglu.githubchallenge.viewobjects.RepoSearchResult
import java.io.IOException

/**
 * A task that reads the search result in the database and fetches the next page, if it has one.
 */
class FetchNextSearchPageTask constructor(
        private val query: String,
        private val githubService: GithubService,
        private val db: GithubDb
) : Runnable {
    private val _liveData = MutableLiveData<Resource<Boolean>>()
    val liveData: LiveData<Resource<Boolean>> = _liveData

    override fun run() {
        val current = db.repoDao().findSearchResult(query)
        if (current == null) {
            _liveData.postValue(null)
            return
        }
        val nextPage = current.next
        if (nextPage == null) {
            _liveData.postValue(Resource.success(false))
            return
        }
        val newValue = try {
            val response = githubService.searchRepos(query, nextPage).execute()
            val apiResponse = ApiResponse.create(response)
            when (apiResponse) {
                is ApiSuccessResponse -> {
                    // we merge all repo ids into 1 list so that it is easier to fetch the
                    // result list.
                    val ids = arrayListOf<Int>()
                    ids.addAll(current.repoIds)

                    ids.addAll(apiResponse.body.items.map { it.id })
                    val merged = RepoSearchResult(
                            query,
                            ids,
                            apiResponse.body.total,
                            apiResponse.nextPage
                    )
                    try {
                        db.beginTransaction()
                        db.repoDao().insert(merged)
                        db.repoDao().insertRepos(apiResponse.body.items)
                        db.setTransactionSuccessful()
                    } finally {
                        db.endTransaction()
                    }
                    Resource.success(apiResponse.nextPage != null)
                }
                is ApiEmptyResponse -> Resource.success(false)
                is ApiErrorResponse -> Resource.error(apiResponse.errorMessage, true)
            }

        } catch (e: IOException) {
            Resource.error(e.message!!, true)
        }
        _liveData.postValue(newValue)
    }
}

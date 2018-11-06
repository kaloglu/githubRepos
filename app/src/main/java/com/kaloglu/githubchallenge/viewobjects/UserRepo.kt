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

package com.kaloglu.githubchallenge.viewobjects

import android.arch.persistence.room.Embedded
import com.google.gson.annotations.SerializedName

open class UserRepo(
        open val id: Int,
        @field:SerializedName("name")
        open val name: String,
        @field:SerializedName("full_name")
        open val fullName: String,
        @field:SerializedName("description")
        open val description: String?,
        @field:SerializedName("owner")
        @field:Embedded(prefix = "owner_")
        open val owner: Owner,
        @field:SerializedName("stargazers_count")
        open val stars: Int = 0,
        @field:SerializedName("default_branch")
        open val branch: String? = "",
        @field:SerializedName("language")
        open val lang: String? = "",
        @field:SerializedName("forks_count")
        open val forks: Int = 0
) {

    data class Owner(
            @field:SerializedName("login")
            val login: String,
            @field:SerializedName("url")
            val url: String?,
            @field:SerializedName("avatar_url")
            val avatar: String? = ""
    )

    companion object {
        const val UNKNOWN_ID = -1
    }
}

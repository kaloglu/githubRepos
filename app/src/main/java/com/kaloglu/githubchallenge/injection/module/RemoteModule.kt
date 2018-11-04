package com.kaloglu.githubchallenge.injection.module

import com.kaloglu.githubchallenge.injection.scopes.PerApplication
import com.kaloglu.githubchallenge.BuildConfig
import com.kaloglu.githubchallenge.api.GithubService
import com.kaloglu.githubchallenge.utils.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
abstract class RemoteModule {

    @Module
    companion object {

        @JvmStatic
        @PerApplication
        @Provides
        fun provideGithubService(): GithubService {
            return Retrofit.Builder()
                    .baseUrl(BuildConfig.END_POINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(LiveDataCallAdapterFactory())
                    .build()
                    .create(GithubService::class.java)
        }

    }

}

package com.kaloglu.githubchallenge.injection.module

import android.content.Context
import com.kaloglu.githubchallenge.injection.qualifier.ApplicationContext
import com.kaloglu.githubchallenge.injection.scopes.PerApplication
import com.kaloglu.githubchallenge.GitHubChallengeApp
import dagger.Binds
import dagger.Module

@Module
abstract class ApplicationModule {

    @ApplicationContext
    @PerApplication
    @Binds
    abstract fun bindApplication(application: GitHubChallengeApp): Context


}
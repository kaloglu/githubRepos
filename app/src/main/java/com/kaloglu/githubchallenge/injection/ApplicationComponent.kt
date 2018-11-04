package com.kaloglu.githubchallenge.injection

import android.app.Application
import com.kaloglu.githubchallenge.injection.scopes.PerApplication
import com.kaloglu.githubchallenge.GitHubChallengeApp
import com.kaloglu.githubchallenge.injection.module.ActivityBindingModule
import com.kaloglu.githubchallenge.injection.module.ApplicationModule
import com.kaloglu.githubchallenge.injection.module.DataModule
import com.kaloglu.githubchallenge.injection.module.PreferencesModule
import com.kaloglu.githubchallenge.injection.module.RemoteModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@PerApplication
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    PreferencesModule::class,
    ActivityBindingModule::class,
    DataModule::class,
    RemoteModule::class
])
interface ApplicationComponent : AndroidInjector<GitHubChallengeApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    override fun inject(githubApp: GitHubChallengeApp)
}

package com.kaloglu.githubchallenge.injection.module

import com.kaloglu.githubchallenge.injection.scopes.PerActivity
import com.kaloglu.githubchallenge.mobileui.main.MainActivity
import com.kaloglu.githubchallenge.injection.module.main.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ActivityModule::class])
abstract class ActivityBindingModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun contributesRepoListActivity(): MainActivity

}

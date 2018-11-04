package com.kaloglu.githubchallenge.injection.module

import com.kaloglu.githubchallenge.injection.scopes.PerActivity
import com.kaloglu.githubchallenge.mobileui.main.MainActivity
import com.kaloglu.githubchallenge.injection.main.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ActivityModule::class])
abstract class ActivityBindingModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun contributesRepoListActivity(): MainActivity

}

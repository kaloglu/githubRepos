package com.kaloglu.githubchallenge.injection.module.main

import com.kaloglu.githubchallenge.domain.interfaces.main.MainContract
import com.kaloglu.githubchallenge.domain.main.MainPresenter
import com.kaloglu.githubchallenge.injection.module.search.SearchModule
import com.kaloglu.githubchallenge.injection.scopes.PerActivity
import com.kaloglu.githubchallenge.injection.scopes.PerFragment
import com.kaloglu.githubchallenge.mobileui.base.BaseActivity
import com.kaloglu.githubchallenge.mobileui.main.MainActivity
import com.kaloglu.githubchallenge.mobileui.search.SearchFragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        @PerActivity
        fun presenter()
                : MainContract.Presenter = MainPresenter()
    }

    @Binds
    abstract fun main(activity: MainActivity): BaseActivity

    @PerFragment
    @ContributesAndroidInjector(modules = [SearchModule::class])
    abstract fun search(): SearchFragment
}

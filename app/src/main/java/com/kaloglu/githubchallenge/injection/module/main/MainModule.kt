package com.kaloglu.githubchallenge.injection.module.main

import android.support.v4.app.FragmentManager
import com.kaloglu.githubchallenge.domain.interfaces.main.MainContract
import com.kaloglu.githubchallenge.domain.main.MainPresenter
import com.kaloglu.githubchallenge.injection.module.ActivityModule
import com.kaloglu.githubchallenge.injection.module.repo.RepoModule
import com.kaloglu.githubchallenge.injection.module.search.SearchModule
import com.kaloglu.githubchallenge.injection.module.user.UserModule
import com.kaloglu.githubchallenge.injection.scopes.PerActivity
import com.kaloglu.githubchallenge.injection.scopes.PerFragment
import com.kaloglu.githubchallenge.mobileui.base.BaseActivity
import com.kaloglu.githubchallenge.mobileui.main.MainActivity
import com.kaloglu.githubchallenge.mobileui.repo.RepoFragment
import com.kaloglu.githubchallenge.mobileui.search.SearchFragment
import com.kaloglu.githubchallenge.mobileui.user.UserFragment
import com.kaloglu.githubchallenge.navigation.FragmentNavigator
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module(includes = [ActivityModule::class])
abstract class MainModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        @PerActivity
        fun presenter(): MainContract.Presenter = MainPresenter()

//        @JvmStatic
//        @Provides
//        @PerActivity
//        fun fragmentNavigator(fragmentManager: FragmentManager): FragmentNavigator {
//            return FragmentNavigator(fragmentManager)
//        }
    }

    @Binds
    @PerActivity
    abstract fun main(activity: MainActivity): BaseActivity

    @PerFragment
    @ContributesAndroidInjector(modules = [SearchModule::class])
    abstract fun search(): SearchFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [RepoModule::class])
    abstract fun repo(): RepoFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [UserModule::class])
    abstract fun user(): UserFragment
}

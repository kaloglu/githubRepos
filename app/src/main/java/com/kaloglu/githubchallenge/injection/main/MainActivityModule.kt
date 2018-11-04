package com.kaloglu.githubchallenge.injection.main

import com.kaloglu.githubchallenge.domain.RepoRepository
import com.kaloglu.githubchallenge.injection.scopes.PerActivity
import com.kaloglu.githubchallenge.domain.interfaces.main.MainActivityContract
import com.kaloglu.githubchallenge.domain.main.MainActivityPresenter
import com.kaloglu.githubchallenge.mobileui.base.BaseActivity
import com.kaloglu.githubchallenge.mobileui.main.MainActivity
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class MainActivityModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        @PerActivity
        fun provideMainActivityPresenter(repoRepository: RepoRepository)
                : MainActivityContract.Presenter = MainActivityPresenter(repoRepository)
    }

    @Binds
    abstract fun bindMainActivity(activity: MainActivity): BaseActivity
}

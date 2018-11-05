package com.kaloglu.githubchallenge.injection.module.detail

import com.kaloglu.githubchallenge.domain.repo.RepoPresenter
import com.kaloglu.githubchallenge.domain.interfaces.repo.RepoContract
import com.kaloglu.githubchallenge.injection.scopes.PerFragment
import com.kaloglu.githubchallenge.navigation.FragmentNavigator
import dagger.Module
import dagger.Provides

@Module
abstract class RepoModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        @PerFragment
        fun presenter(fragmentNavigator: FragmentNavigator)
                : RepoContract.Presenter = RepoPresenter(fragmentNavigator)
    }

}

package com.kaloglu.githubchallenge.injection.module.detail

import com.kaloglu.githubchallenge.domain.RepoRepository
import com.kaloglu.githubchallenge.domain.detail.DetailPresenter
import com.kaloglu.githubchallenge.domain.interfaces.detail.DetailContract
import com.kaloglu.githubchallenge.injection.scopes.PerFragment
import com.kaloglu.githubchallenge.navigation.FragmentNavigator
import dagger.Module
import dagger.Provides

@Module
abstract class DetailModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        @PerFragment
        fun presenter(repository: RepoRepository, fragmentNavigator: FragmentNavigator)
                : DetailContract.Presenter = DetailPresenter(repository, fragmentNavigator)
    }

}

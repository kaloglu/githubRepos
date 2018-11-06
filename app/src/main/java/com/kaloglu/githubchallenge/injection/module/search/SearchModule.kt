package com.kaloglu.githubchallenge.injection.module.search

import com.kaloglu.githubchallenge.domain.repo.RepoRepository
import com.kaloglu.githubchallenge.domain.interfaces.search.SearchContract
import com.kaloglu.githubchallenge.domain.search.SearchPresenter
import com.kaloglu.githubchallenge.injection.scopes.PerFragment
import com.kaloglu.githubchallenge.navigation.FragmentNavigator
import dagger.Module
import dagger.Provides

@Module
abstract class SearchModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        @PerFragment
        fun presenter(repository: RepoRepository, fragmentNavigator: FragmentNavigator)
                : SearchContract.Presenter = SearchPresenter(repository,fragmentNavigator)
    }

}

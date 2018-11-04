package com.kaloglu.githubchallenge.injection.module.search

import com.kaloglu.githubchallenge.domain.RepoRepository
import com.kaloglu.githubchallenge.domain.interfaces.search.SearchContract
import com.kaloglu.githubchallenge.domain.search.SearchPresenter
import com.kaloglu.githubchallenge.injection.scopes.PerFragment
import dagger.Module
import dagger.Provides

@Module
abstract class SearchModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        @PerFragment
        fun presenter(repository: RepoRepository)
                : SearchContract.Presenter = SearchPresenter(repository)
    }

}

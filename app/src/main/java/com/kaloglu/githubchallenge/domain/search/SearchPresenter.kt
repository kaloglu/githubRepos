package com.kaloglu.githubchallenge.domain.search

import android.arch.lifecycle.Observer
import com.kaloglu.githubchallenge.domain.RepoRepository
import com.kaloglu.githubchallenge.domain.interfaces.search.SearchContract
import com.kaloglu.githubchallenge.mobileui.base.mvp.BaseAbstractPresenter
import javax.inject.Inject

class SearchPresenter @Inject constructor(private val repository: RepoRepository)
    : BaseAbstractPresenter<SearchContract.View>(), SearchContract.Presenter {

    override fun repoSearch(query: String): Boolean {
        if (query.length > 2) {
            repository.search(query)
                    .observe(
                            getView().lifeCycleOwner,
                            Observer(getView().liveData::postValue)
                    )
            return true
        }

        return false
    }

}
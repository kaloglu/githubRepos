package com.kaloglu.githubchallenge.domain.main

import android.arch.lifecycle.Observer
import com.kaloglu.githubchallenge.domain.RepoRepository
import com.kaloglu.githubchallenge.domain.interfaces.main.MainActivityContract
import com.kaloglu.githubchallenge.mobileui.base.mvp.BaseAbstractPresenter
import javax.inject.Inject

class MainActivityPresenter @Inject constructor(private val repoRepository: RepoRepository)
    : BaseAbstractPresenter<MainActivityContract.View>(), MainActivityContract.Presenter {

    override fun repoSearch(query: String) {
        repoRepository.search(query)
                .observe(
                        getView().lifeCycleOwner,
                        Observer(getView().liveData::postValue)
                )
    }

}
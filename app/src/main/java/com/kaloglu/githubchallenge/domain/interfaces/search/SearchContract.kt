package com.kaloglu.githubchallenge.domain.interfaces.search

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.support.v7.widget.SearchView
import com.kaloglu.githubchallenge.domain.interfaces.base.mvp.BasePresenter
import com.kaloglu.githubchallenge.domain.interfaces.base.mvp.BaseView
import com.kaloglu.githubchallenge.viewobjects.Repo
import com.kaloglu.githubchallenge.viewobjects.Resource

interface SearchContract {

    interface View : BaseView, SearchView.OnQueryTextListener {
        val liveData: MutableLiveData<Resource<List<Repo>>>
        val lifeCycleOwner: LifecycleOwner

        fun showResult(list: List<Repo>)
        fun showNoResult()
        fun showError(status: String?)
        fun showProgress()
        fun setTitle(name: String)
        fun hideKeyboard()
    }

    interface Presenter : BasePresenter<View> {
        fun repoSearch(query: String): Boolean
        fun showRepoFragment(repo: Repo)
        fun showUserFragment(login: String)
    }
}

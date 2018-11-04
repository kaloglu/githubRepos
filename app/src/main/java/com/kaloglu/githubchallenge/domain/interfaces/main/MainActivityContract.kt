package com.kaloglu.githubchallenge.domain.interfaces.main

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.support.v7.widget.SearchView
import com.kaloglu.githubchallenge.mobileui.base.mvp.BasePresenter
import com.kaloglu.githubchallenge.mobileui.base.mvp.BaseView
import com.kaloglu.githubchallenge.viewobjects.Repo
import com.kaloglu.githubchallenge.viewobjects.Resource

interface MainActivityContract {

    interface View : BaseView, SearchView.OnQueryTextListener {
        val liveData: MutableLiveData<Resource<List<Repo>>>
        val lifeCycleOwner: LifecycleOwner

        fun fillTheRecyclerView(list: List<Repo>)
    }

    interface Presenter : BasePresenter<View> {
        fun repoSearch(query: String)
    }
}
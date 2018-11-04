package com.kaloglu.githubchallenge.domain.interfaces.detail

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import com.kaloglu.githubchallenge.domain.interfaces.base.mvp.BasePresenter
import com.kaloglu.githubchallenge.domain.interfaces.base.mvp.BaseView
import com.kaloglu.githubchallenge.viewobjects.Repo
import com.kaloglu.githubchallenge.viewobjects.Resource

interface DetailContract {

    interface View : BaseView {
        val liveData: MutableLiveData<Resource<Repo>>
        val lifeCycleOwner: LifecycleOwner

        fun showError(status: String?)
        fun showProgress()
        fun showResult(repo: Repo)
    }

    interface Presenter : BasePresenter<View> {
        fun showRepoDetail(repo: Repo?)
    }
}

package com.kaloglu.githubchallenge.domain.interfaces.user

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import com.kaloglu.githubchallenge.domain.interfaces.base.mvp.BasePresenter
import com.kaloglu.githubchallenge.domain.interfaces.base.mvp.BaseView
import com.kaloglu.githubchallenge.viewobjects.Resource
import com.kaloglu.githubchallenge.viewobjects.UserRepo

interface UserContract {

    interface View : BaseView {
        val liveData: MutableLiveData<Resource<List<UserRepo>>>
        val lifeCycleOwner: LifecycleOwner

        fun showError(status: String?)
        fun showProgress()
        fun showNoResult()
        fun showResult(repos: List<UserRepo>)
        fun setTitle(username: String)
    }

    interface Presenter : BasePresenter<View> {
        fun showRepoFragment(repo: UserRepo)
        fun showUser(username: String)
        fun showUserFragment(login: String)
    }
}

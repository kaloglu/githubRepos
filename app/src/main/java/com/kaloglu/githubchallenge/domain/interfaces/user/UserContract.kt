package com.kaloglu.githubchallenge.domain.interfaces.user

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import com.kaloglu.githubchallenge.viewobjects.User
import com.kaloglu.githubchallenge.domain.interfaces.base.mvp.BasePresenter
import com.kaloglu.githubchallenge.domain.interfaces.base.mvp.BaseView
import com.kaloglu.githubchallenge.viewobjects.Repo
import com.kaloglu.githubchallenge.viewobjects.Resource

interface UserContract {

    interface View : BaseView {
        val liveData: MutableLiveData<Resource<User>>
        val lifeCycleOwner: LifecycleOwner

        fun showError(status: String?)
        fun showProgress()
        fun showResult(user: User)
        fun setTitle(username: String)
    }

    interface Presenter : BasePresenter<View> {
        fun showRepoFragment(repo: Repo)
        fun showUserDetail(user: User)
    }
}

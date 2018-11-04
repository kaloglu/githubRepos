package com.kaloglu.githubchallenge.domain.interfaces.main

import com.kaloglu.githubchallenge.domain.interfaces.base.mvp.BasePresenter
import com.kaloglu.githubchallenge.domain.interfaces.base.mvp.BaseView

interface MainContract {

    interface View : BaseView {
        fun setNavigationOnClick(onClick: (android.view.View) -> Unit)
    }

    interface Presenter : BasePresenter<View>
}

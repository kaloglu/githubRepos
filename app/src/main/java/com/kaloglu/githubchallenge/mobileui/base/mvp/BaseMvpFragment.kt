package com.kaloglu.githubchallenge.mobileui.base.mvp

import android.os.Bundle
import android.view.View
import com.kaloglu.githubchallenge.domain.interfaces.base.mvp.BasePresenter
import com.kaloglu.githubchallenge.domain.interfaces.base.mvp.BaseView
import com.kaloglu.githubchallenge.mobileui.base.BaseFragment
import javax.inject.Inject

abstract class BaseMvpFragment<P : BasePresenter<*>> : BaseFragment(), BaseView {

    @Inject
    lateinit var presenter: P

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        onPresenterAttached()
    }

    override fun onDestroyView() {
        onPresenterDetached()
        presenter.detachView()
        super.onDestroyView()
    }

    protected open fun onPresenterAttached() {
        // Override this on child fragments if needed.
    }

    protected open fun onPresenterDetached() {
        // Override this on child fragments if needed.
    }
}

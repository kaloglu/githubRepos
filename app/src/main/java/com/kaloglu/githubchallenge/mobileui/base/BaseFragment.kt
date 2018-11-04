package com.kaloglu.githubchallenge.mobileui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment

abstract class BaseFragment : DaggerFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflateView(inflater, container)

    open fun inflateView(inflater: LayoutInflater, container: ViewGroup?): View =
            inflater.inflate(getResourceLayoutId(), container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUserInterface(view)
    }

    /**
     * Get fragment's UI content layout resource id.
     *
     * @return The fragment's root view's resource id
     */
    protected abstract fun getResourceLayoutId(): Int

    /**
     * Initialize UI content elements.
     *
     * @param rootView The fragment's root view
     */
    protected abstract fun initUserInterface(rootView: View)

    /**
     * Finds and returns the frame layout's id which holds the fragment.
     *
     * @return id as int
     */
    fun getContainerFrameLayoutId() = (view?.parent as ViewGroup).id

    fun getFragmentTag() = this.javaClass.simpleName

}

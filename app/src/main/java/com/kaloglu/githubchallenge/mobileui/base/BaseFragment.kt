package com.kaloglu.githubchallenge.mobileui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment

abstract class BaseFragment : DaggerFragment() {

    /**
     * Get fragment's UI content layout resource id.
     *
     * @return The fragment's root view's resource id
     */
    protected abstract val resourceLayoutId: Int

    val fragmentTag = this.javaClass.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflateView(inflater, container)

    open fun inflateView(inflater: LayoutInflater, container: ViewGroup?): View =
            inflater.inflate(resourceLayoutId, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUserInterface(view)
    }

    /**
     * Finds and returns the frame layout's id which holds the fragment.
     *
     * @return id as int
     */
    fun getContainerFrameLayoutId(): Int? {
        return (view?.parent as ViewGroup).id
    }

    /**
     * Initialize UI content elements.
     *
     * @param rootView The fragment's root view
     */
    protected abstract fun initUserInterface(rootView: View)

}

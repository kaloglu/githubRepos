package com.kaloglu.githubchallenge.mobileui.base

import android.os.Bundle
import com.kaloglu.githubchallenge.R
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity() {

    /**
     * Method to get activity's UI content layout resource id.
     * if overwritten [getBaseFrameLayoutId][.getBaseFrameLayoutId] must also be implemented.
     *
     * @return The activity's content's resource id
     */
    protected open val contentResourceId = R.layout.activity_base

    /**
     * Method to get activity's UI content frame layout resource id.
     *
     * @return The activity's frame layout resource id
     */
    protected open val baseFrameLayoutId = R.id.activity_base_fragment_container

    /**
     * Get initial fragment instance.
     *
     * @return Fragment
     */
    protected abstract val containedFragment: BaseFragment?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView()

        initUserInterface()

        if (savedInstanceState == null) {

            containedFragment?.run {
                supportFragmentManager.beginTransaction()
                        .add(baseFrameLayoutId, this, fragmentTag)
                        .commit()
            }
        }
    }

    protected open fun setContentView() {
        setContentView(contentResourceId)
    }

    /**
     * Initialize UI content elements.
     */
    protected abstract fun initUserInterface()
}

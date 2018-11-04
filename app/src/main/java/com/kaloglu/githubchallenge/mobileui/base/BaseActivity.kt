package com.kaloglu.githubchallenge.mobileui.base

import android.os.Bundle
import com.kaloglu.githubchallenge.R
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView()

        initUserInterface()

        if (savedInstanceState == null) {

            if (containedFragment != null) {

                val tag = containedFragment!!.getFragmentTag()

                supportFragmentManager.beginTransaction()
                        .add(baseFrameLayoutId, containedFragment, tag)
                        .commit()
            }
        }
    }

    //Bu method'un ismi değişebilir
    protected open fun setContentView() {
        setContentView(contentResourceId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    /**
     * Initialize UI content elements.
     */
    protected abstract fun initUserInterface()

    /**
     * Method to get activity's UI content layout resource id.
     * if overwritten [getBaseFrameLayoutId][.getBaseFrameLayoutId] must also be implemented.
     *
     * @return The activity's content's resource id
     */
    protected open val contentResourceId: Int
        get() = R.layout.activity_base

    /**
     * Method to get activity's UI content frame layout resource id.
     *
     * @return The activity's frame layout resource id
     */
    protected open val baseFrameLayoutId: Int
        get() = R.id.activity_base_fragment_container

    /**
     * Get initial fragment instance.
     *
     * @return Fragment
     */
    protected abstract val containedFragment: BaseFragment?
}

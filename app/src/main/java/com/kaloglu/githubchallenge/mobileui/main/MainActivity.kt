package com.kaloglu.githubchallenge.mobileui.main

import android.content.Context
import android.content.Intent
import android.support.annotation.StringRes
import android.view.View
import com.kaloglu.githubchallenge.R
import com.kaloglu.githubchallenge.domain.interfaces.main.MainContract
import com.kaloglu.githubchallenge.mobileui.base.BaseFragment
import com.kaloglu.githubchallenge.mobileui.base.mvp.BaseMvpActivity
import com.kaloglu.githubchallenge.mobileui.search.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseMvpActivity<MainContract.Presenter>(),
        MainContract.View {

    override val contentResourceId = R.layout.activity_main

    override val baseFrameLayoutId = R.id.activity_fragment_container

    override val containedFragment: BaseFragment? = SearchFragment()

    override fun initUserInterface() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        setNavigationOnClick {
            onBackPressed()
        }
    }

    override fun setNavigationOnClick(onClick: (View) -> Unit) =
            toolbar?.setNavigationOnClickListener {
                onClick(it)
            } ?: Unit

    override fun setTitle(@StringRes resId: Int) {
        toolbar?.title = getString(resId)
    }

    companion object {

        @JvmStatic
        fun newIntent(context: Context): Intent = Intent(context, MainActivity::class.java)
    }

}

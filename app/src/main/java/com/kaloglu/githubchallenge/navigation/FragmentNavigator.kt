package com.kaloglu.githubchallenge.navigation

import android.support.annotation.IdRes
import android.support.v4.app.FragmentManager
import com.kaloglu.githubchallenge.R
import com.kaloglu.githubchallenge.injection.scopes.PerActivity
import com.kaloglu.githubchallenge.mobileui.base.BaseFragment
import javax.inject.Inject

@PerActivity
class FragmentNavigator @Inject constructor(
        private val fragmentManager: FragmentManager,
        @IdRes private val containerId: Int = R.id.activity_fragment_container
) {

    fun showFragment(fragment: BaseFragment) {
        fragmentManager.beginTransaction()
                .replace(containerId, fragment)
                .addToBackStack(fragment.fragmentTag)
                .commit()
    }

    fun popBackStack() {
        fragmentManager.popBackStack()
    }

    fun clearPopStack() {
        var count = fragmentManager.backStackEntryCount
        while (count > 1) {
            fragmentManager.popBackStack()
            count--
        }
    }

}

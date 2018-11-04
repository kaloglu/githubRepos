package com.kaloglu.githubchallenge.navigation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.kaloglu.githubchallenge.injection.scopes.PerActivity
import com.kaloglu.githubchallenge.mobileui.main.MainActivity
import javax.inject.Inject

@PerActivity
class ActivityNavigator @Inject constructor(private val activity: Activity) {

    fun finishCurrentActivity(): NavigationCreator {
        return NavigationCreator(activity).finishThis()
    }

    fun openExternalUrl(url: String): NavigationCreator {
        return NavigationCreator(activity)
                .intent(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    fun toRepoListActivity(): NavigationCreator {
        return NavigationCreator(activity)
                .intent(MainActivity.newIntent(activity))
    }

}

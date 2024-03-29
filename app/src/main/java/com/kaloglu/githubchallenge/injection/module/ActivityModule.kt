package com.kaloglu.githubchallenge.injection.module

import android.app.Activity
import android.content.Context
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.kaloglu.githubchallenge.injection.qualifier.ActivityContext
import com.kaloglu.githubchallenge.injection.scopes.PerActivity
import com.kaloglu.githubchallenge.mobileui.base.BaseActivity
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class ActivityModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        @PerActivity
        fun provideFragmentManager(activity: AppCompatActivity): FragmentManager {
            return activity.supportFragmentManager
        }
    }

    @Binds
    @PerActivity
    abstract fun bindAppCompatActivity(activity: BaseActivity): AppCompatActivity

    @Binds
    @PerActivity
    abstract fun bindActivity(activity: AppCompatActivity): Activity

    @ActivityContext
    @Binds
    @PerActivity
    abstract fun bindActivityContext(activity: Activity): Context
}

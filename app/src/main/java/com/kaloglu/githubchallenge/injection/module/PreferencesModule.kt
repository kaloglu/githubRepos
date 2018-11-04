package com.kaloglu.githubchallenge.injection.module

import android.content.Context
import com.kaloglu.githubchallenge.injection.qualifier.ApplicationContext
import com.kaloglu.githubchallenge.injection.scopes.PerApplication
import com.kaloglu.githubchallenge.data.LocalStorage
import dagger.Module
import dagger.Provides

@Module
class PreferencesModule {

    @PerApplication
    @Provides
    fun providesSharedDataPreferences(@ApplicationContext context: Context): LocalStorage {
        return LocalStorage(context)
    }
}

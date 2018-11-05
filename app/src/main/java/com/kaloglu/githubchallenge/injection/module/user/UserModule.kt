package com.kaloglu.githubchallenge.injection.module.user

import com.kaloglu.githubchallenge.domain.interfaces.user.UserContract
import com.kaloglu.githubchallenge.domain.user.UserPresenter
import com.kaloglu.githubchallenge.injection.scopes.PerFragment
import com.kaloglu.githubchallenge.navigation.FragmentNavigator
import dagger.Module
import dagger.Provides

@Module
abstract class UserModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        @PerFragment
        fun presenter(fragmentNavigator: FragmentNavigator)
                : UserContract.Presenter = UserPresenter(fragmentNavigator)
    }

}

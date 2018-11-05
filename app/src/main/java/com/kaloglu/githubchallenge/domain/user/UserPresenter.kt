package com.kaloglu.githubchallenge.domain.user

import com.kaloglu.githubchallenge.domain.interfaces.user.UserContract
import com.kaloglu.githubchallenge.mobileui.base.mvp.BaseAbstractPresenter
import com.kaloglu.githubchallenge.mobileui.repo.RepoFragment
import com.kaloglu.githubchallenge.navigation.FragmentNavigator
import com.kaloglu.githubchallenge.viewobjects.Repo
import com.kaloglu.githubchallenge.viewobjects.User
import javax.inject.Inject

class UserPresenter
@Inject constructor(private val fragmentNavigator: FragmentNavigator)
    : BaseAbstractPresenter<UserContract.View>(), UserContract.Presenter {

    override fun showUserDetail(user: User) = getView().showResult(user)

    override fun showRepoFragment(repo: Repo) {
        getView().setTitle(repo.name)
        fragmentNavigator.showFragment(
                RepoFragment().apply {
                    this.repo = repo
                }
        )

    }


}
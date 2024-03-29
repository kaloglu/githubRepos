package com.kaloglu.githubchallenge.domain.repo

import com.kaloglu.githubchallenge.domain.interfaces.repo.RepoContract
import com.kaloglu.githubchallenge.mobileui.base.mvp.BaseAbstractPresenter
import com.kaloglu.githubchallenge.mobileui.user.UserFragment
import com.kaloglu.githubchallenge.navigation.FragmentNavigator
import com.kaloglu.githubchallenge.viewobjects.Repo
import javax.inject.Inject

class RepoPresenter
@Inject constructor(private val fragmentNavigator: FragmentNavigator)
    : BaseAbstractPresenter<RepoContract.View>(), RepoContract.Presenter {

    override fun showRepoDetail(repo: Repo) = getView().showResult(repo)

    override fun showUserFragment(username: String) {
        getView().setTitle(username)
        fragmentNavigator.showFragment(
                UserFragment().apply {
                    this.username = username
                }
        )

    }
}
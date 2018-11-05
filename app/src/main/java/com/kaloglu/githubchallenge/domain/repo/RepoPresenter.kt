package com.kaloglu.githubchallenge.domain.repo

import com.kaloglu.githubchallenge.domain.interfaces.repo.RepoContract
import com.kaloglu.githubchallenge.mobileui.base.mvp.BaseAbstractPresenter
import com.kaloglu.githubchallenge.navigation.FragmentNavigator
import com.kaloglu.githubchallenge.viewobjects.Repo
import javax.inject.Inject

class RepoPresenter
@Inject constructor(private val fragmentNavigator: FragmentNavigator)
    : BaseAbstractPresenter<RepoContract.View>(), RepoContract.Presenter {

    override fun showRepoDetail(repo: Repo) = getView().showResult(repo)

    override fun showUserFragment(owner: Repo.Owner) {
//        getView().setTitle(owner.login)
//        fragmentNavigator.showFragment(
//                OwnerFragment().apply {
//                    this.owner = owner
//                }
//        )

    }
}
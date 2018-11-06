package com.kaloglu.githubchallenge.domain.user

import android.arch.lifecycle.Observer
import com.kaloglu.githubchallenge.domain.interfaces.user.UserContract
import com.kaloglu.githubchallenge.domain.repo.RepoRepository
import com.kaloglu.githubchallenge.mobileui.base.mvp.BaseAbstractPresenter
import com.kaloglu.githubchallenge.mobileui.repo.RepoFragment
import com.kaloglu.githubchallenge.mobileui.user.UserFragment
import com.kaloglu.githubchallenge.navigation.FragmentNavigator
import com.kaloglu.githubchallenge.viewobjects.Repo
import com.kaloglu.githubchallenge.viewobjects.UserRepo
import javax.inject.Inject

class UserPresenter
@Inject constructor(
        private val repository: RepoRepository,
        private val fragmentNavigator: FragmentNavigator
) : BaseAbstractPresenter<UserContract.View>(), UserContract.Presenter {

    override fun showUser(username: String) {
        repository.loadRepos(username).observe(
                getView().lifeCycleOwner,
                Observer(getView().liveData::postValue)
        )
    }

    override fun showRepoFragment(repo: UserRepo) {
        getView().setTitle(repo.name)
        fragmentNavigator.showFragment(
                RepoFragment().apply {
                    this.repo = repo.run {
                        Repo(this)
                    }
                }
        )

    }

    override fun showUserFragment(login: String) {
        getView().setTitle(login)
        fragmentNavigator.showFragment(
                UserFragment().apply {
                    this.username = login
                }
        )

    }


}
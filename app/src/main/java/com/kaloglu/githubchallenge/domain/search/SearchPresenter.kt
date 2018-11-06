package com.kaloglu.githubchallenge.domain.search

import android.arch.lifecycle.Observer
import com.kaloglu.githubchallenge.domain.repo.RepoRepository
import com.kaloglu.githubchallenge.domain.interfaces.search.SearchContract
import com.kaloglu.githubchallenge.mobileui.repo.RepoFragment
import com.kaloglu.githubchallenge.mobileui.base.mvp.BaseAbstractPresenter
import com.kaloglu.githubchallenge.mobileui.user.UserFragment
import com.kaloglu.githubchallenge.navigation.FragmentNavigator
import com.kaloglu.githubchallenge.viewobjects.Repo
import javax.inject.Inject

class SearchPresenter
@Inject constructor(
        private val repository: RepoRepository,
        private val fragmentNavigator: FragmentNavigator
) : BaseAbstractPresenter<SearchContract.View>(), SearchContract.Presenter {

    override fun showUserFragment(login: String) {
        getView().setTitle(login)
        fragmentNavigator.showFragment(
                UserFragment().apply {
                    this.username = login
                }
        )

        getView().hideKeyboard()
    }

    override fun showRepoFragment(repo: Repo) {
        getView().setTitle(repo.name)
        fragmentNavigator.showFragment(
                RepoFragment().apply {
                    this.repo = repo
                }
        )

        getView().hideKeyboard()
    }

    override fun repoSearch(query: String): Boolean {
        repository.search(query)
                .observe(
                        getView().lifeCycleOwner,
                        Observer(getView().liveData::postValue)
                )
        return true
    }

}
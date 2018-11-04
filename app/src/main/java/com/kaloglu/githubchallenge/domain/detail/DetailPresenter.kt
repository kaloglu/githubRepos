package com.kaloglu.githubchallenge.domain.detail

import android.arch.lifecycle.Observer
import com.kaloglu.githubchallenge.domain.RepoRepository
import com.kaloglu.githubchallenge.domain.interfaces.detail.DetailContract
import com.kaloglu.githubchallenge.mobileui.base.mvp.BaseAbstractPresenter
import com.kaloglu.githubchallenge.navigation.FragmentNavigator
import com.kaloglu.githubchallenge.viewobjects.Repo
import javax.inject.Inject

class DetailPresenter
@Inject constructor(private val repository: RepoRepository, private val fragmentNavigator: FragmentNavigator)
    : BaseAbstractPresenter<DetailContract.View>(), DetailContract.Presenter {

    override fun showRepoDetail(repo: Repo?) {
        repo?.run {
            repository.loadRepo(owner.login, name).observe(
                    getView().lifeCycleOwner,
                    Observer(getView().liveData::postValue)
            )
        }
    }


}
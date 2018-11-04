package com.kaloglu.githubchallenge.domain.search

import android.arch.lifecycle.Observer
import com.kaloglu.githubchallenge.domain.RepoRepository
import com.kaloglu.githubchallenge.domain.interfaces.search.SearchContract
import com.kaloglu.githubchallenge.mobileui.base.mvp.BaseAbstractPresenter
import com.kaloglu.githubchallenge.navigation.FragmentNavigator
import com.kaloglu.githubchallenge.viewobjects.Repo
import javax.inject.Inject

class SearchPresenter
@Inject constructor(
        private val repository: RepoRepository,
        private val fragmentNavigator: FragmentNavigator
)
    : BaseAbstractPresenter<SearchContract.View>(), SearchContract.Presenter {

    override fun showDetailFragment(item: Repo) {
//        fragmentNavigator.showFragment(
//                DetailFragment().apply {
//                    arguments = Bundle().apply {
//                        putString(DetailFragment.ARG_ITEM_ID, item.id.toString())
//                    }
//                }
//        )
    }

    override fun repoSearch(query: String): Boolean {
        if (query.length > 2) {
            repository.search(query)
                    .observe(
                            getView().lifeCycleOwner,
                            Observer(getView().liveData::postValue)
                    )
            return true
        }

        return false
    }

}
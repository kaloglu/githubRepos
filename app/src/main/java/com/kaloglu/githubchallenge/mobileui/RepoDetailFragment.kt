package com.kaloglu.githubchallenge.mobileui

import android.arch.lifecycle.MutableLiveData
import android.view.View
import android.widget.Toast
import com.kaloglu.githubchallenge.R
import com.kaloglu.githubchallenge.domain.interfaces.detail.DetailContract
import com.kaloglu.githubchallenge.mobileui.base.mvp.BaseMvpFragment
import com.kaloglu.githubchallenge.mobileui.main.MainActivity
import com.kaloglu.githubchallenge.utils.observe
import com.kaloglu.githubchallenge.viewobjects.Repo
import com.kaloglu.githubchallenge.viewobjects.Resource
import com.kaloglu.githubchallenge.viewobjects.Status
import kotlinx.android.synthetic.main.fragment_repo_detail.*
import kotlinx.android.synthetic.main.repo_detail.*

class RepoDetailFragment : BaseMvpFragment<DetailContract.Presenter>(), DetailContract.View {

    override val liveData: MutableLiveData<Resource<Repo>> = MutableLiveData()
    override val lifeCycleOwner = this
    override val resourceLayoutId = R.layout.fragment_repo_detail

    var repo: Repo? = null

    override fun initUserInterface(rootView: View) {
        (activity as MainActivity).setSupportActionBar(detail_toolbar)
        liveData.observe(lifeCycleOwner) {
            it?.run {
                when (status) {
                    Status.LOADING -> showProgress()
                    Status.ERROR -> showError(message)
                    else -> showResult(data!!)
                }
            }
        }
    }

    override fun showResult(repo: Repo) {
        repo_detail.text = repo.description
    }

    override fun showError(status: String?) = Toast.makeText(context, status, Toast.LENGTH_SHORT).show()

    override fun showProgress() = Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()

    override fun onPresenterAttached() {
        repo?.also(presenter::showRepoDetail)
    }
}

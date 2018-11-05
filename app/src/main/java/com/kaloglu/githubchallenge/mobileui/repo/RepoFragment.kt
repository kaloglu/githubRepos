package com.kaloglu.githubchallenge.mobileui.repo

import android.arch.lifecycle.MutableLiveData
import android.view.View
import android.widget.Toast
import com.kaloglu.githubchallenge.R
import com.kaloglu.githubchallenge.domain.interfaces.detail.RepoContract
import com.kaloglu.githubchallenge.mobileui.base.mvp.BaseMvpFragment
import com.kaloglu.githubchallenge.viewobjects.Repo
import com.kaloglu.githubchallenge.viewobjects.Resource
import kotlinx.android.synthetic.main.fragment_repo_detail.*

class RepoFragment : BaseMvpFragment<RepoContract.Presenter>(), RepoContract.View {

    override val liveData: MutableLiveData<Resource<Repo>> = MutableLiveData()
    override val lifeCycleOwner = this
    override val resourceLayoutId = R.layout.fragment_repo_detail

    var repo: Repo? = null

    override fun initUserInterface(rootView: View) {
        username.setOnClickListener {
            val owner = repo?.owner
            if (username.text.isNotEmpty() && owner?.login.toString().isNotEmpty())
                presenter.showUserFragment(owner!!)
        }
    }

    override fun setTitle(username: String) {
        activity?.title = username
    }

    override fun showResult(repo: Repo) {
        repo.run {

            repo.text = name
            username.text = owner.login
            fork_count.text = forks.toString()
            star_count.text = stars.toString()
            lang.text = lang.toString()
            branch.text = branch.toString()
            id_description.text = description.toString()
        }
    }

    override fun showError(status: String?) = Toast.makeText(context, status, Toast.LENGTH_SHORT).show()

    override fun showProgress() = Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()

    override fun onPresenterAttached() {
        repo?.also(presenter::showRepoDetail)
    }
}

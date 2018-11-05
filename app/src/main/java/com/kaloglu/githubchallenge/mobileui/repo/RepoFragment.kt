package com.kaloglu.githubchallenge.mobileui.repo

import android.arch.lifecycle.MutableLiveData
import android.view.View
import android.widget.Toast
import com.kaloglu.githubchallenge.R
import com.kaloglu.githubchallenge.domain.interfaces.repo.RepoContract
import com.kaloglu.githubchallenge.mobileui.base.mvp.BaseMvpFragment
import com.kaloglu.githubchallenge.viewobjects.Repo
import com.kaloglu.githubchallenge.viewobjects.Resource
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_repo_detail.*

class RepoFragment : BaseMvpFragment<RepoContract.Presenter>(), RepoContract.View {

    override val liveData: MutableLiveData<Resource<Repo>> = MutableLiveData()
    override val lifeCycleOwner = this
    override val resourceLayoutId = R.layout.fragment_repo_detail

    var repo: Repo? = null

    override fun initUserInterface(rootView: View) {
        textViewUsername.setOnClickListener {
            val username = repo?.owner?.login.toString()
            if (textViewUsername.text.isNotEmpty() && username.isNotEmpty())
                presenter.showUserFragment(username)
        }
    }

    override fun setTitle(username: String) {
        activity?.title = username
    }

    override fun showResult(repo: Repo) {
        repo.run {
            Picasso.get()
                    .load(repo.owner.avatar)
                    .into(imageViewProfile)
            textViewRepo.text = name
            textViewUsername.text = owner.login
            textViewForkCount.text = forks.toString()
            textViewStarCount.text = stars.toString()
            textViewLang.text = lang.toString()
            textViewBranch.text = branch.toString()
            textViewDescription.text = description.toString()
        }
    }

    override fun showError(status: String?) = Toast.makeText(context, status, Toast.LENGTH_SHORT).show()

    override fun showProgress() = Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()

    override fun onPresenterAttached() {
        repo?.also(presenter::showRepoDetail)
    }
}

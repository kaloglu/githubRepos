package com.kaloglu.githubchallenge.mobileui.user

import android.arch.lifecycle.MutableLiveData
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.kaloglu.githubchallenge.R
import com.kaloglu.githubchallenge.domain.interfaces.user.UserContract
import com.kaloglu.githubchallenge.mobileui.ItemAdapter
import com.kaloglu.githubchallenge.mobileui.base.mvp.BaseMvpFragment
import com.kaloglu.githubchallenge.utils.observe
import com.kaloglu.githubchallenge.viewobjects.Repo
import com.kaloglu.githubchallenge.viewobjects.Resource
import com.kaloglu.githubchallenge.viewobjects.Status
import com.kaloglu.githubchallenge.viewobjects.UserRepo
import kotlinx.android.synthetic.main.fragment_user_detail.*
import kotlinx.android.synthetic.main.repo_list.view.*

class UserFragment : BaseMvpFragment<UserContract.Presenter>(), UserContract.View {
    override val resourceLayoutId = R.layout.fragment_user_detail
    override val liveData = MutableLiveData<Resource<List<UserRepo>>>()
    override val lifeCycleOwner = this

    var username: String = ""

    private lateinit var adapter: ItemAdapter

    override fun initUserInterface(rootView: View) {
        adapter = frameLayout.repo_list.setup()

        adapter.onClickItem = { presenter.showRepoFragment(it as UserRepo) }
        adapter.onClickProfile = { presenter.showUserFragment((it as Repo).owner.login) }

        liveData.observe(lifeCycleOwner) {
            it?.run {
                when {
                    status == Status.LOADING -> showProgress()
                    status == Status.ERROR -> showError(message)
                    status == Status.SUCCESS && data.isNullOrEmpty() -> showNoResult()
                    else -> showResult(data!!)
                }
            }
        }

    }

    override fun setTitle(username: String) {
        activity?.title = username
    }

    override fun showResult(repos: List<UserRepo>) {
        adapter.values = repos
    }

    override fun showNoResult() = Toast.makeText(context, "Sonuç yok", Toast.LENGTH_SHORT).show()

    override fun showError(status: String?) = Toast.makeText(context, status, Toast.LENGTH_SHORT).show()

    override fun showProgress() = Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()

    private fun RecyclerView.setup(): ItemAdapter {
        layoutManager = LinearLayoutManager(context)
        adapter = ItemAdapter()

        addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        return adapter as ItemAdapter
    }

    override fun onPresenterAttached() = presenter.showUser(username)

}

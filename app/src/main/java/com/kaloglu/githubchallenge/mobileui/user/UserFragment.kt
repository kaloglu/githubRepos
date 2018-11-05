package com.kaloglu.githubchallenge.mobileui.user

import android.arch.lifecycle.MutableLiveData
import android.view.View
import android.widget.Toast
import com.kaloglu.githubchallenge.viewobjects.User
import com.kaloglu.githubchallenge.R
import com.kaloglu.githubchallenge.domain.interfaces.user.UserContract
import com.kaloglu.githubchallenge.mobileui.base.mvp.BaseMvpFragment
import com.kaloglu.githubchallenge.viewobjects.Resource

class UserFragment : BaseMvpFragment<UserContract.Presenter>(), UserContract.View {

    override val liveData: MutableLiveData<Resource<User>> = MutableLiveData()
    override val lifeCycleOwner = this
    override val resourceLayoutId = R.layout.fragment_user_detail

    var username: String = ""

    override fun initUserInterface(rootView: View) {
//        username.setOnClickListener {
//            val user = user?.user
//            if (username.text.isNotEmpty() && user?.login.toString().isNotEmpty())
//                presenter.showUserFragment(user!!)
//        }
    }

    override fun setTitle(username: String) {
        activity?.title = username
    }

    override fun showResult(user: User) {
        user.run {

//            user.text = name
//            username.text = user.login
//            fork_count.text = forks.toString()
//            star_count.text = stars.toString()
//            lang.text = lang.toString()
//            branch.text = branch.toString()
//            id_description.text = description.toString()
        }
    }

    override fun showError(status: String?) = Toast.makeText(context, status, Toast.LENGTH_SHORT).show()

    override fun showProgress() = Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()

    override fun onPresenterAttached() {
        presenter::showUserDetail
    }
}

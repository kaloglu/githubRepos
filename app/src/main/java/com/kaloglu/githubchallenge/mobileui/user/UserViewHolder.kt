package com.kaloglu.githubchallenge.mobileui.user

import android.support.constraint.ConstraintLayout
import android.view.View
import android.view.View.VISIBLE
import com.kaloglu.githubchallenge.mobileui.ItemAdapter
import com.kaloglu.githubchallenge.viewobjects.UserRepo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.repo_list_content_at_user_detail.view.*

class UserViewHolder(view: View) : ItemAdapter.ItemViewHolder<UserRepo>(view) {

    val repoView: ConstraintLayout = view.repoLayout
    val userView: ConstraintLayout = view.userLayout

    override fun bind(item: UserRepo) {
        if (showUserInfo) {
            bindUserInfo(item.owner)
        }
        bindRepoInfo(item)
    }

    private fun bindRepoInfo(repo: UserRepo) {
        repoView.run {
            textViewRepo.text = repo.name
            textViewForks.text = repo.forks.toString()
            textViewStar.text = repo.stars.toString()
        }
    }

    private fun bindUserInfo(owner: UserRepo.Owner) {
        userView.run {
            visibility = VISIBLE
            textViewUsername.text = owner.login
            textViewUrl.text = owner.url
            Picasso.get().load(owner.avatar).into(imageViewProfile)
        }
    }

}
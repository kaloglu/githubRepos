package com.kaloglu.githubchallenge.mobileui.repo

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.kaloglu.githubchallenge.mobileui.ItemAdapter
import com.kaloglu.githubchallenge.viewobjects.Repo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.repo_list_content.view.*

class RepoViewHolder(view: View) : ItemAdapter.ItemViewHolder<Repo>(view) {

    val idView: TextView = view.textViewUsername
    val contentView: TextView = view.textViewRepo
    val imageView: ImageView = view.imageViewProfile

    override fun bind(item: Repo) {
        idView.text = item.name
        contentView.text = item.owner.login
        Picasso.get().load(item.owner.avatar).into(imageView)

    }
}
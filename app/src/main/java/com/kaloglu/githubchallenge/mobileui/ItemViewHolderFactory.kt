package com.kaloglu.githubchallenge.mobileui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.kaloglu.githubchallenge.R
import com.kaloglu.githubchallenge.mobileui.repo.RepoViewHolder
import com.kaloglu.githubchallenge.mobileui.user.UserViewHolder

class ItemViewHolderFactory {
    companion object Factory {
        fun create(layoutInflater: LayoutInflater, parent: ViewGroup, viewType: Int) =
                when (viewType) {
                    1 -> {
                        val view = layoutInflater.inflate(R.layout.repo_list_content_at_user_detail, parent, false)
                        UserViewHolder(view)
                    }
                    else -> {
                        val view = layoutInflater.inflate(R.layout.repo_list_content, parent, false)
                        RepoViewHolder(view)
                    }
                }

        @Suppress("UNCHECKED_CAST")
        fun <VH : ItemAdapter.ItemViewHolder<*>> of(holder: RecyclerView.ViewHolder) =
                holder as VH
    }
}
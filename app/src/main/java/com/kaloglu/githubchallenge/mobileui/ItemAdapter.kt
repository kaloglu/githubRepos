package com.kaloglu.githubchallenge.mobileui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kaloglu.githubchallenge.mobileui.repo.RepoViewHolder
import com.kaloglu.githubchallenge.mobileui.user.UserViewHolder
import com.kaloglu.githubchallenge.viewobjects.UserRepo
import kotlinx.android.synthetic.main.repo_list_content.view.*

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ItemViewHolder<*>>() {

    var onClickItem: ((Any) -> Unit) = {}
    var onClickProfile: ((Any) -> Unit) = {}

    var values = emptyList<Any>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemViewType(position: Int) =
            when (values[position]) {
                is UserRepo -> 1
                else -> 0
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ItemViewHolderFactory.create(LayoutInflater.from(parent.context), parent, viewType)

    override fun getItemCount() = values.size

    override fun onBindViewHolder(holder: ItemViewHolder<*>, position: Int) =
            when (getItemViewType(position)) {
                1 -> ItemViewHolderFactory.of<UserViewHolder>(holder)
                else -> ItemViewHolderFactory.of<RepoViewHolder>(holder)
            }.bind(values[position], onClickItem, onClickProfile, position == 0)

    abstract class ItemViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var showUserInfo: Boolean = false

        abstract fun bind(item: T)

        @Suppress("UNCHECKED_CAST")
        internal fun bind(item: Any, onClick: (T) -> Unit, onClickProfile: (T) -> Unit, showUserInfo: Boolean = false) {
            item as T
            with(itemView) {
                tag = item
                setOnClickListener { onClick(item) }
            }

            //just quick workaround
            itemView.imageViewProfile?.setOnClickListener {
                onClickProfile(item)
            }
            this.showUserInfo = showUserInfo
            bind(item)

        }

    }

}


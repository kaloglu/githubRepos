package com.kaloglu.githubchallenge.mobileui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.kaloglu.githubchallenge.R
import com.kaloglu.githubchallenge.viewobjects.Repo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.repo_list_content.view.*

class SimpleItemRecyclerViewAdapter : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener

    var onClickItem: ((Repo) -> Unit) = {}

    var values = emptyList<Repo>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as Repo

            onClickItem(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.repo_list_content, parent, false)
        return ViewHolder(view)
    }

    //TODO: this just sample :)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.owner.login
        holder.contentView.text = item.name
        Picasso.get()
                .load(item.owner.avatar)
                .into(holder.imageView)

        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount() = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.textViewUsername
        val contentView: TextView = view.textViewRepo
        val imageView: ImageView = view.imageViewProfile
    }
}
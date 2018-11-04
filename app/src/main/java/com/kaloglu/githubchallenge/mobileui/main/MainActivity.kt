package com.kaloglu.githubchallenge.mobileui.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import com.kaloglu.githubchallenge.R
import com.kaloglu.githubchallenge.domain.interfaces.main.MainActivityContract
import com.kaloglu.githubchallenge.mobileui.SimpleItemRecyclerViewAdapter
import com.kaloglu.githubchallenge.mobileui.base.BaseFragment
import com.kaloglu.githubchallenge.mobileui.base.mvp.BaseMvpActivity
import com.kaloglu.githubchallenge.viewobjects.Repo
import com.kaloglu.githubchallenge.viewobjects.Resource
import com.kaloglu.githubchallenge.viewobjects.Status
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.repo_list.*
import android.support.v7.widget.DividerItemDecoration
import com.kaloglu.githubchallenge.mobileui.RepoDetailActivity
import com.kaloglu.githubchallenge.mobileui.RepoDetailFragment


class MainActivity : BaseMvpActivity<MainActivityContract.Presenter>(),
        MainActivityContract.View {

    override val liveData = MutableLiveData<Resource<List<Repo>>>()

    override val lifeCycleOwner = this

    override val baseFrameLayoutId = R.id.activity_fragment_container

    override val contentResourceId = R.layout.activity_main

    private lateinit var adapter: SimpleItemRecyclerViewAdapter

    override fun initUserInterface() {
        setSupportActionBar(toolbar)
        toolbar.title = title

        adapter = repo_list.setup()

        adapter.onClickItem = {

            val fragment = RepoDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(RepoDetailFragment.ARG_ITEM_ID, it.id.toString())
                }
            }

            supportFragmentManager.beginTransaction()
                    .replace(baseFrameLayoutId, fragment)
                    .commit()

            frameLayout.visibility = GONE
        }

        liveData.observe(this, Observer {
            it?.run {
                when {
                    status == Status.LOADING -> Toast.makeText(this@MainActivity, "Loading", Toast.LENGTH_SHORT).show()
                    status == Status.ERROR -> Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
                    status == Status.SUCCESS && data.isNullOrEmpty() -> Toast.makeText(this@MainActivity, "Sonuç yok", Toast.LENGTH_SHORT).show()
                    else -> fillTheRecyclerView(data!!)
                }
            }
        })
    }

    override fun fillTheRecyclerView(list: List<Repo>) {
        adapter.values = list
        if (adapter.values.isNotEmpty()) {
            frameLayout.visibility = VISIBLE
            emptyText.visibility = GONE
        } else {
            emptyText.visibility = VISIBLE
            frameLayout.visibility = GONE
        }

    }

    override val containedFragment: BaseFragment?
        get() = null

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.dashboard, menu)

        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.queryHint = getString(R.string.search_hint)
        searchView.setOnQueryTextListener(this)
        searchView.isIconified = true
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String) = true

    override fun onQueryTextChange(newText: String): Boolean {

        if (newText.length > 2) {
            presenter.repoSearch(newText)
            return true
        }

        return false
    }

    companion object {

        @JvmStatic
        fun newIntent(context: Context): Intent = Intent(context, MainActivity::class.java)
    }

    private fun RecyclerView.setup(): SimpleItemRecyclerViewAdapter {
        layoutManager = LinearLayoutManager(context)
        adapter = SimpleItemRecyclerViewAdapter()

        addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        return adapter as SimpleItemRecyclerViewAdapter
    }

}

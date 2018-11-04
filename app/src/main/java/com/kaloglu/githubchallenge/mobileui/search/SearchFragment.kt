package com.kaloglu.githubchallenge.mobileui.search

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.support.design.widget.TextInputEditText
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.kaloglu.githubchallenge.R
import com.kaloglu.githubchallenge.domain.interfaces.search.SearchContract
import com.kaloglu.githubchallenge.mobileui.SimpleItemRecyclerViewAdapter
import com.kaloglu.githubchallenge.mobileui.base.mvp.BaseMvpFragment
import com.kaloglu.githubchallenge.viewobjects.Repo
import com.kaloglu.githubchallenge.viewobjects.Resource
import com.kaloglu.githubchallenge.viewobjects.Status
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.repo_list.view.*

class SearchFragment : BaseMvpFragment<SearchContract.Presenter>(), SearchContract.View {
    override val resourceLayoutId = R.layout.fragment_search
    override val liveData = MutableLiveData<Resource<List<Repo>>>()
    override val lifeCycleOwner = this

    private lateinit var adapter: SimpleItemRecyclerViewAdapter

    override fun initUserInterface(rootView: View) {
        adapter = frameLayout.repo_list.setup()

        adapter.onClickItem = {

            //            val fragment = RepoDetailFragment().apply {
//                arguments = Bundle().apply {
//                    putString(RepoDetailFragment.ARG_ITEM_ID, it.id.toString())
//                }
//            }
//
//            supportFragmentManager.beginTransaction()
//                    .replace(baseFrameLayoutId, fragment)
//                    .commit()
//
//            frameLayout.visibility = View.GONE
        }

        liveData.observe(this, Observer {
            it?.run {
                when {
                    status == Status.LOADING -> showProgress()
                    status == Status.ERROR -> showError(message)
                    status == Status.SUCCESS && data.isNullOrEmpty() -> showNoResult()
                    else -> fillTheRecyclerView(data!!)
                }
            }
        })

        searchView.setup(this)

    }

    override fun fillTheRecyclerView(list: List<Repo>) {
        adapter.values = list
        if (adapter.values.isNotEmpty()) {
            frameLayout.visibility = View.VISIBLE
            emptyText.visibility = View.GONE
        } else {
            emptyText.visibility = View.VISIBLE
            frameLayout.visibility = View.GONE
        }

    }

    override fun onQueryTextSubmit(query: String) = presenter.repoSearch(query)

    override fun onQueryTextChange(newText: String) = newText.length > 2

    private fun showNoResult() = Toast.makeText(context, "Sonuç yok", Toast.LENGTH_SHORT).show()

    private fun showError(status: String?) = Toast.makeText(context, status, Toast.LENGTH_SHORT).show()

    private fun showProgress() = Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()

    private fun RecyclerView.setup(): SimpleItemRecyclerViewAdapter {
        layoutManager = LinearLayoutManager(context)
        adapter = SimpleItemRecyclerViewAdapter()

        addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        return adapter as SimpleItemRecyclerViewAdapter
    }

    private fun TextInputEditText.setup(onQueryTextListener: SearchView.OnQueryTextListener) {
        hint = getString(R.string.search_hint)

        //TODO: workaround
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) = Unit

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (onQueryTextListener.onQueryTextChange(p0.toString()))
                    onQueryTextListener.onQueryTextSubmit(p0.toString())
            }
        })
    }

}


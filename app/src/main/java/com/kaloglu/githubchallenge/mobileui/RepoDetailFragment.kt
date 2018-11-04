package com.kaloglu.githubchallenge.mobileui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kaloglu.githubchallenge.R
import com.kaloglu.githubchallenge.viewobjects.Repo
import kotlinx.android.synthetic.main.activity_repo_detail.*
import kotlinx.android.synthetic.main.repo_detail.view.*

/**
 * A fragment representing a single Repo detail screen.
 * This fragment is either contained in a [MainActivity]
 * in two-pane mode (on tablets) or a [RepoDetailActivity]
 * on handsets.
 */
class RepoDetailFragment : Fragment() {

    /**
     * The dummy content this fragment is presenting.
     */
    private var item: Repo? = Repo(
            -1,
            "test",
            "abc",
            "description",
            Repo.Owner("kaloglu", "asda"),
            5
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
//                item = it.getString(ARG_ITEM_ID)]
                activity?.toolbar_layout?.title = "test"
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.repo_detail, container, false)

        // Show the dummy content as text in a TextView.
        item?.let {
            rootView.repo_detail.text = it.description
        }

        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}

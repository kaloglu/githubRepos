package com.kaloglu.githubchallenge.mobileui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.kaloglu.githubchallenge.R
import com.kaloglu.githubchallenge.mobileui.main.MainActivity
import kotlinx.android.synthetic.main.activity_repo_detail.*

class RepoDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_detail)
        setSupportActionBar(detail_toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
//        if (savedInstanceState == null) {
////             Create the detail fragment and add it to the activity
////             using a fragment transaction.
//            val fragment = RepoDetailFragment().apply {
//                arguments = Bundle().apply {
//                    putString(RepoDetailFragment.ARG_ITEM_ID,
//                            intent.getStringExtra(RepoDetailFragment.ARG_ITEM_ID))
//                }
//            }
//
//            supportFragmentManager.beginTransaction()
//                    .add(R.id.repo_detail_container, fragment)
//                    .commit()
//        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                android.R.id.home -> {
                    // This ID represents the Home or Up button. In the case of this
                    // activity, the Up button is shown. For
                    // more details, see the Navigation pattern on Android Design:
                    //
                    // http://developer.android.com/design/patterns/navigation.html#up-vs-back

                    navigateUpTo(Intent(this, MainActivity::class.java))
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    companion object {

        @JvmStatic
        fun newIntent(context: Context): Intent = Intent(context, RepoDetailActivity::class.java)
    }
}

package com.emmanuelkehinde.githubstalker.ui.repo_list

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.emmanuelkehinde.githubstalker.App
import com.emmanuelkehinde.githubstalker.R
import com.emmanuelkehinde.githubstalker.data.model.Repo
import com.emmanuelkehinde.githubstalker.ui.repo_detail.RepoDetailActivity
import com.emmanuelkehinde.githubstalker.ui.repo_list.adapter.RepoListAdapter
import com.emmanuelkehinde.githubstalker.helper.Constants
import com.emmanuelkehinde.githubstalker.helper.showLongToast
import com.emmanuelkehinde.shutdown.Shutdown
import kotlinx.android.synthetic.main.activity_repo_list.*

class RepoListActivity : AppCompatActivity(), RepoListView, SwipeRefreshLayout.OnRefreshListener, RepoListAdapter.RepoListListener {

    private lateinit var repoListPresenter: RepoListPresenter
    private lateinit var repoListAdapter: RepoListAdapter
    private var repos: ArrayList<Repo> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_list)
        supportActionBar?.title = getString(R.string.repoListActivityTitle)
        swipeLayout.setColorSchemeColors(resources.getColor(R.color.colorAccent))
        swipeLayout.setOnRefreshListener(this)

        repoListPresenter = RepoListPresenterImpl(this).apply {
            App.getInstance().getDependencyComponent().inject(this)
        }

        setUpRecyclerView()
        savedInstanceState?.getParcelableArrayList<Repo>(Constants.REPOS_KEY)?.let {
            this.repos = it
            displayRepos(it)
        } ?: repoListPresenter.fetchRepos()
    }

    private fun setUpRecyclerView() {
        repoListAdapter = RepoListAdapter(this,this)
        recyclerRepoList.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        recyclerRepoList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerRepoList.adapter = repoListAdapter
    }

    private fun displayRepos(repos: ArrayList<Repo>) {
        repoListAdapter.setRepoListAndRefresh(repos)
    }

    override fun onRepoFetchSuccess(repos: ArrayList<Repo>) {
        if (repos.isEmpty()) {
            if (this.repos.isEmpty()) {
                showNoRepoLayout()
            }
        } else {
            this.repos = repos
            displayRepos(repos)
        }
    }

    override fun onRepoFetchFailure(throwable: Throwable?) {
        showLongToast(throwable?.localizedMessage!!)
        if (this.repos.isEmpty()) {
            showNoRepoLayout()
        }
    }

    override fun onRepoClicked(position: Int) {
        val intent = Intent(this, RepoDetailActivity::class.java).apply {
            putExtra(Constants.REPO_EXTRA,repos[position])
        }
        startActivity(intent)
    }

    private fun showNoRepoLayout() {
        layoutNoRepo.visibility = View.VISIBLE
        recyclerRepoList.visibility = View.GONE
    }

    private fun hideNoRepoLayout() {
        if (layoutNoRepo.visibility == View.VISIBLE) {
            layoutNoRepo.visibility = View.GONE
            recyclerRepoList.visibility = View.VISIBLE
        }
    }

    override fun showLoader() {
        swipeLayout.isRefreshing = true
    }

    override fun hideLoader() {
        swipeLayout.isRefreshing = false
    }

    override fun onRefresh() {
        hideNoRepoLayout()
        repoListPresenter.fetchRepos()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelableArrayList(Constants.REPOS_KEY,repos)
    }

    override fun onDestroy() {
        super.onDestroy()
        repoListPresenter.destroyView()
    }

    override fun onBackPressed() {
        Shutdown.now(this)
    }
}

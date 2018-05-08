package com.emmanuelkehinde.githubstalker.ui.repo_list

import com.emmanuelkehinde.githubstalker.data.model.Repo

interface RepoListView {

    fun showLoader()
    fun hideLoader()
    fun onRepoFetchSuccess(repos: ArrayList<Repo>)
    fun onRepoFetchFailure(throwable: Throwable?)

}
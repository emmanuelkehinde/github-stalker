package com.emmanuelkehinde.githubstalker.ui.repo_list

interface RepoListPresenter {
    fun fetchRepos()
    fun destroyView()
}
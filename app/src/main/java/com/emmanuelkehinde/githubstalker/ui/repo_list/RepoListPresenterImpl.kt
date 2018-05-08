package com.emmanuelkehinde.githubstalker.ui.repo_list

import javax.inject.Inject

class RepoListPresenterImpl(var repoListView: RepoListView?) : RepoListPresenter {

    @Inject
    lateinit var repoListRepository: RepoListRepository

    override fun fetchRepos() {
        repoListView?.showLoader()

        repoListRepository.fetchRepos({
            repoListView?.hideLoader()
            repoListView?.onRepoFetchSuccess(it)

        },{
            repoListView?.hideLoader()
            repoListView?.onRepoFetchFailure(it)

        })
    }

    override fun destroyView() {
        repoListView = null
    }

}
package com.emmanuelkehinde.githubstalker.di.component

import com.emmanuelkehinde.githubstalker.di.module.AppModule
import com.emmanuelkehinde.githubstalker.di.module.DependencyModule
import com.emmanuelkehinde.githubstalker.ui.repo_detail.RepoDetailActivity
import com.emmanuelkehinde.githubstalker.ui.repo_list.RepoListPresenterImpl
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (DependencyModule::class)])
interface DependencyComponent {
    fun inject(repoListPresenterImpl: RepoListPresenterImpl)
    fun inject(repoDetailActivity: RepoDetailActivity)
}
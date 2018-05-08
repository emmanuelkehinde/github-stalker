package com.emmanuelkehinde.githubstalker.di.module

import com.emmanuelkehinde.githubstalker.data.local.RepoDatabase
import com.emmanuelkehinde.githubstalker.data.remote.ApiService
import com.emmanuelkehinde.githubstalker.ui.repo_list.RepoListRepository
import com.emmanuelkehinde.githubstalker.util.NetworkUtil
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DependencyModule {

    @Provides
    @Singleton
    fun provideRepoListRepository(apiService: ApiService, repoDatabase: RepoDatabase, networkUtil: NetworkUtil): RepoListRepository {
        return RepoListRepository(apiService, repoDatabase, networkUtil)
    }

}
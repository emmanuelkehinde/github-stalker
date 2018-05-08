package com.emmanuelkehinde.githubstalker.ui.repo_list

import com.emmanuelkehinde.githubstalker.data.local.RepoDatabase
import com.emmanuelkehinde.githubstalker.data.model.Repo
import com.emmanuelkehinde.githubstalker.data.model.response.RepoResponse
import com.emmanuelkehinde.githubstalker.data.remote.ApiService
import com.emmanuelkehinde.githubstalker.util.NetworkUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch


class RepoListRepository(var apiService: ApiService, var repoDatabase: RepoDatabase, var networkUtil: NetworkUtil) {

    fun fetchRepos(successHandler: (ArrayList<Repo>) -> Unit, failureHandler: (Throwable?) -> Unit) {
        if (networkUtil.isOnline()) {
            fetchOnline(successHandler,failureHandler)
        } else {
            fetchLocally(successHandler)
        }
    }

    private fun fetchOnline(successHandler: (ArrayList<Repo>) -> Unit, failureHandler: (Throwable?) -> Unit) {
        apiService.fetchRepos()
                .enqueue(object : Callback<RepoResponse> {
                    override fun onFailure(call: Call<RepoResponse>?, t: Throwable?) {
                        failureHandler(t)
                    }

                    override fun onResponse(call: Call<RepoResponse>?, response: Response<RepoResponse>?) {
                        response?.let {
                            val repos = it.body()!!.data
                            saveIntoDatabase(repos)
                            successHandler(repos)
                        }
                    }

                })
    }

    private fun saveIntoDatabase(repos: ArrayList<Repo>) {
        launch {
            if (!repos.isEmpty()) {
                repoDatabase.clearAllTables() //Clear the table first
                repoDatabase.repoDao().insertRepos(repos)
            }
        }
    }

    private fun fetchLocally(successHandler: (ArrayList<Repo>) -> Unit) {
        launch(UI) {
            val repos: ArrayList<Repo> = async(CommonPool) {
                repoDatabase.repoDao().getRepos() as ArrayList<Repo>
            }.await()
            successHandler(repos)
        }
    }
}
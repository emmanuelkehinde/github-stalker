package com.emmanuelkehinde.githubstalker.data.remote

import com.emmanuelkehinde.githubstalker.data.model.response.RepoResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("search/repositories?q=android+language:java&sort=stars&order=desc")
    fun fetchRepos(): Call<RepoResponse>
}
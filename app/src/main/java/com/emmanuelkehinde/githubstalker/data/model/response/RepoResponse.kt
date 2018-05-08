package com.emmanuelkehinde.githubstalker.data.model.response

import com.emmanuelkehinde.githubstalker.data.model.Repo
import com.google.gson.annotations.SerializedName

class RepoResponse {

    @SerializedName("items")
    lateinit var data: ArrayList<Repo>
}
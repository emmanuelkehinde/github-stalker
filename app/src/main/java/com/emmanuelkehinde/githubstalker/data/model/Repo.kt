package com.emmanuelkehinde.githubstalker.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "repo")
data class Repo (
        @PrimaryKey @SerializedName("id") var id: String,
        @SerializedName("name") var name: String,
        @SerializedName("full_name") var fullName: String,
        @SerializedName("html_url") var url: String,
        @SerializedName("description") var description: String,
        @SerializedName("forks") var forks: String,
        @SerializedName("watchers") var stars: String,
        @SerializedName("owner") var owner: Owner
): Parcelable
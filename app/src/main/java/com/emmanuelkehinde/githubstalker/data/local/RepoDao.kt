package com.emmanuelkehinde.githubstalker.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.emmanuelkehinde.githubstalker.data.model.Repo

@Dao
interface RepoDao {

    @Query("SELECT * FROM repo")
    fun getRepos(): List<Repo>

    @Insert
    @JvmSuppressWildcards
    fun insertRepos(users: List<Repo>)

}
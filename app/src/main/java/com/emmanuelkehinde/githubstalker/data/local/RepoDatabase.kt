package com.emmanuelkehinde.githubstalker.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.emmanuelkehinde.githubstalker.data.local.converters.ObjectConverter
import com.emmanuelkehinde.githubstalker.data.model.Repo

@Database(entities = [(Repo::class)], version = 1, exportSchema = false)
@TypeConverters((ObjectConverter::class))
abstract class RepoDatabase: RoomDatabase() {
    abstract fun repoDao(): RepoDao
}
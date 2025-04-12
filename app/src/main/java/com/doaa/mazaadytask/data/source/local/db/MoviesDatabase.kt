package com.doaa.mazaadytask.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.doaa.mazaadytask.data.source.local.db.converter.Converters
import com.doaa.mazaadytask.core.models.Movie
import com.doaa.mazaadytask.core.models.Genre
import com.doaa.mazaadytask.data.source.local.db.daos.CategoryRemoteKeysDao
import com.doaa.mazaadytask.data.source.local.db.daos.GenreDao
import com.doaa.mazaadytask.data.source.local.db.entities.CategoryRemoteKeys

@Database(entities = [Movie::class, Genre::class, CategoryRemoteKeys::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MoviesDatabase : RoomDatabase(){
    abstract fun movieDao(): MovieDao
    abstract fun genreDao(): GenreDao
    abstract fun categoryRemoteKeysDao(): CategoryRemoteKeysDao

}
package com.doaa.mazaadytask.data.source.local.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.doaa.mazaadytask.core.models.Genre
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(genres: List<Genre>)

    @Query("SELECT * FROM genre ORDER BY name ASC")
    fun getGenres():Flow<List<Genre>>
    @Query("SELECT * FROM genre WHERE id IN (:movieIds)")
     fun getMoviesByIds(movieIds: List<Int>):Flow<List<Genre>>








}
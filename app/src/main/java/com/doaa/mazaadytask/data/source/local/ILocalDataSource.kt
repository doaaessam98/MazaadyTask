package com.doaa.mazaadytask.data.source.local

import com.doaa.mazaadytask.data.source.local.db.MoviesDatabase
import com.doaa.mazaadytask.core.models.Genre
import com.doaa.mazaadytask.core.models.Movie
import kotlinx.coroutines.flow.Flow


interface ILocalDataSource {
    val databaseObject: MoviesDatabase
    suspend fun addToFavourite(id: Int):Int
    suspend fun removeFromFavourite(id: Int):Int
    fun insertAllGenres(genres:List<Genre>)
    fun  getGenres():Flow<List<Genre>>
    fun  getMovieGenresByIds(genres:List<Int>):Flow<List<Genre>>
}
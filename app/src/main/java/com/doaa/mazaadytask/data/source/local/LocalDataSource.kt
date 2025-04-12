package com.doaa.mazaadytask.data.source.local

import com.doaa.mazaadytask.data.source.local.db.MoviesDatabase
import com.doaa.mazaadytask.core.models.Genre
import com.doaa.mazaadytask.core.models.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


 class LocalDataSource @Inject constructor(private val database: MoviesDatabase): ILocalDataSource {

    override val databaseObject: MoviesDatabase
        get() = database

    override suspend fun addToFavourite(id: Int):Int {
      return  databaseObject.movieDao().addFavourite(id)
    }




     override suspend fun removeFromFavourite(id: Int): Int {
         return database.movieDao().removeFromFavourite(id)
     }


     override fun insertAllGenres(genres: List<Genre>) {
         database.genreDao().insertAll(genres)
     }

     override fun getGenres(): Flow<List<Genre>> {
         return database.genreDao().getGenres()
     }

     override fun getMovieGenresByIds(genres: List<Int>): Flow<List<Genre>> {
         return database.genreDao().getMoviesByIds(genres)
     }

 }
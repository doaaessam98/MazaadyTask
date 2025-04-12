package com.doaa.mazaadytask.data.source.local.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.doaa.mazaadytask.core.models.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun insertAll(movies: List<Movie>)

    @Query("SELECT * FROM movie WHERE genreIds LIKE '%' || :genre || '%'")
    fun getCategoryMovies(genre: String): PagingSource<Int, Movie>


    @Query("DELETE FROM MOVIE WHERE  isUpcoming = 0 AND isPopular=0 AND isTrending=0")
    fun clearMovies()

     @Query("UPDATE  movie set isFav = 1 WHERE id =:id")
      suspend fun addFavourite(id:Int):Int

    @Query("UPDATE  movie set isFav = 0 WHERE id =:id")
    suspend fun removeFromFavourite(id:Int):Int

    @Query("SELECT * FROM movie WHERE  id =:id ")
    suspend fun getMove(id:Int): Movie

    @Query("SELECT * FROM movie WHERE genreIds LIKE :genreId AND (overview LIKE :query OR title LIKE :query)")
    fun getSearchCategoryMovies(query: String, genreId: String): PagingSource<Int, Movie>

//    @Query("SELECT * FROM movie WHERE genreIds LIKE '%' || :genre || '%'")
//    suspend fun getCategoryMoviesList(genre: String): List<Movie>

    @Query("SELECT * FROM movie WHERE ',' || genreIds || ',' LIKE '%,' || :genre || ',%'")
    suspend fun getCategoryMoviesList(genre: String): List<Movie>

    @Query("DELETE FROM MOVIE WHERE ',' || genreIds || ',' LIKE '%,' || :genreId || ',%'")
    suspend fun clearMoviesByGenre(genreId: String) {

    }

}



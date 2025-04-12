package com.doaa.mazaadytask.data.repository.movies

import androidx.paging.PagingData
import com.doaa.mazaadytask.core.base.ResponsResult
import com.doaa.mazaadytask.core.models.Genre
import com.doaa.mazaadytask.core.models.Movie
import kotlinx.coroutines.flow.Flow

interface IRepository {
     suspend fun getGenres(): ResponsResult<Flow<List<Genre>>>
     suspend fun getGenresFromApi()
     fun getMoviesGenres(genreId:String):Flow<PagingData<Movie>>
     suspend fun updateFavouriteState(movieId: Int, isFav: Boolean):ResponsResult<Int>
     suspend fun getSearchMovies(query: String): Flow<PagingData<Movie>>
     fun getSearchCategoryMovie(query: String,categoryId:Int):Flow<PagingData<Movie>>


}



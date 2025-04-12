package com.doaa.mazaadytask.data

import androidx.paging.PagingData
import com.doaa.mazaadytask.core.base.ResponsResult
import com.doaa.mazaadytask.core.models.Genre
import com.doaa.mazaadytask.core.models.Movie
import com.doaa.mazaadytask.data.repository.movies.IRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeRepository : IRepository {
    var genresResult: ResponsResult<Flow<List<Genre>>> = ResponsResult.Success(
        flowOf(listOf(Genre(1, "Action"), Genre(2, "Drama")))
    )

    var pagingMoviesFlow: Flow<PagingData<Movie>> = flowOf(
        PagingData.from(listOf(
            Movie(id = 1, title = "Movie 1"),
            Movie(id = 2, title = "Movie 2")
        ))
    )


    val updatedFavorites = mutableListOf<Pair<Int, Boolean>>()
    var updateFavResponse: ResponsResult<Int> = ResponsResult.Success(1)
    override suspend fun getGenres(): ResponsResult<Flow<List<Genre>>> {
        return genresResult
    }

    override suspend fun getGenresFromApi() {
        TODO("Not yet implemented")
    }

    override fun getMoviesGenres(genreId: String): Flow<PagingData<Movie>> {
       return pagingMoviesFlow
    }

    override suspend fun updateFavouriteState(movieId: Int, isFav: Boolean): ResponsResult<Int> {
        updatedFavorites.add(movieId to isFav)
        return updateFavResponse
    }

    override suspend fun getSearchMovies(query: String): Flow<PagingData<Movie>> {
        TODO("Not yet implemented")
    }


    override fun getSearchCategoryMovie(query: String, categoryId: Int): Flow<PagingData<Movie>> {
        TODO("Not yet implemented")
    }
}
package com.doaa.mazaadytask.data.repository.movies

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.doaa.mazaadytask.core.Utils.Constants.NETWORK_PAGE_SIZE
import com.doaa.mazaadytask.core.base.ResponsResult
import com.doaa.mazaadytask.core.models.Genre
import com.doaa.mazaadytask.core.models.Movie
import com.doaa.mazaadytask.data.paging.CategoryRemoteMediator
import com.doaa.mazaadytask.data.paging.MoviePaginationSearchSource
import com.doaa.mazaadytask.data.source.local.ILocalDataSource
import com.doaa.mazaadytask.data.source.remote.IRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Repository  @Inject constructor(
    private val remoteDataSource: IRemoteDataSource,
    private val localDataSource: ILocalDataSource

    ): IRepository {
    override suspend fun getGenres(): ResponsResult<Flow<List<Genre>>> {
        getGenresFromApi()
        return  try{
            val result = localDataSource.getGenres()
            ResponsResult.Success(result)
        }catch (e:Exception){
            ResponsResult.Error(e.message!!)
        }
    }

    override suspend fun getGenresFromApi(){

        withContext(Dispatchers.IO) {
            try {
                val genres =remoteDataSource.getGenres()
                localDataSource.insertAllGenres(genres.genres)
            } catch (ex:Exception) {

            }
        }
    }

    override fun getMoviesGenres(genreId: String): Flow<PagingData<Movie>> {
        val pagingSourceFactory = {
            localDataSource.databaseObject.movieDao().getCategoryMovies(genreId)
        }
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = remoteDataSource.movieApiServiceObject?.let {
                CategoryRemoteMediator(
                    genreId,
                    it,
                    localDataSource.databaseObject
                )
            },
            pagingSourceFactory = pagingSourceFactory
        ).flow

    }

    override suspend fun updateFavouriteState(movieId: Int, isFav: Boolean): ResponsResult<Int> {
                    return try {

                val response = if (isFav) {
                    localDataSource.addToFavourite(movieId)

                } else {
                    localDataSource.removeFromFavourite(movieId)

                }

                ResponsResult.Success(response)

            }catch (e:Exception){
                ResponsResult.Error(e.message!!)
            }


        }



    override suspend fun getSearchMovies(
        query: String
    ): Flow<PagingData<Movie>> {

        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                    try {
                       MoviePaginationSearchSource(remoteDataSource.movieApiServiceObject, query)
                    } catch (e: Exception) {
                        Log.e("TAG", "getSearchMovies: ${e.message}", )
                       // Log.e("MovieRepo", "Error creating PagingSource", e)
                        throw e
                    }

            }

        ).flow
    }

    override fun getSearchCategoryMovie(query: String, categoryId: Int): Flow<PagingData<Movie>> {
        TODO("Not yet implemented")
    }


}


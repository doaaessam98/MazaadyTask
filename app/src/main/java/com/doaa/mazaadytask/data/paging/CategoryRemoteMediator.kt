package com.doaa.mazaadytask.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.doaa.mazaadytask.core.Utils.Constants
import com.doaa.mazaadytask.core.models.Movie
import com.doaa.mazaadytask.data.source.local.db.MoviesDatabase
import com.doaa.mazaadytask.data.source.local.db.entities.CategoryRemoteKeys
import com.doaa.mazaadytask.data.source.remote.api.MovieApiService
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CategoryRemoteMediator(
    private val genreId: String,
    private val service: MovieApiService,
    private val moviesDatabase: MoviesDatabase
) : RemoteMediator<Int, Movie>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Movie>
    ): RemoteMediator.MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: Constants.MOVIES_STARTING_PAGE_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)

                val prevKey = remoteKeys?.prevKey ?: return RemoteMediator.MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null
                )

                prevKey
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey ?: return RemoteMediator.MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null
                )
                nextKey
            }
        }

        try {
            val response = service.getMovieByGenreCategory(
                with_genres = genreId,
                page = page,
                itemsPerPage = state.config.pageSize
            )

            val movies = response.results
            val endOfPaginationReached = response.page >= response.totalPages

            moviesDatabase.withTransaction {


                if (loadType == LoadType.REFRESH) {
                    val currentMovies = moviesDatabase.movieDao().getCategoryMoviesList(genreId)
                    val movieFlagsMap = currentMovies.associateBy({ it.id }, {
                        Triple(it.isFav, it.isPopular, it.isTrending)

                    })
                    moviesDatabase.categoryRemoteKeysDao().clearRemoteKeys()
                    moviesDatabase.movieDao().clearMoviesByGenre(genreId)
                    movies.forEach { newMovie ->
                        movieFlagsMap[newMovie.id]?.let { (isFav, isPopular, isTrending) ->
                            newMovie.isFav = isFav
                            newMovie.isPopular = isPopular
                            newMovie.isTrending = isTrending
                        }
                    }
                }

                val prevKey = if (page == Constants.MOVIES_STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = movies.map { movie ->
                    CategoryRemoteKeys(MovieId = movie.id, prevKey = prevKey, nextKey = nextKey)
                }

                moviesDatabase.categoryRemoteKeysDao().insertAll(keys)
                moviesDatabase.movieDao().insertAll(movies)
            }
            return RemoteMediator.MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return RemoteMediator.MediatorResult.Error(exception)

        } catch (exception: HttpException) {
            return RemoteMediator.MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Movie>): CategoryRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movie ->
                moviesDatabase.categoryRemoteKeysDao().remoteKeysMovieId(movie.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Movie>): CategoryRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie ->
                moviesDatabase.categoryRemoteKeysDao().remoteKeysMovieId(movie.id)
            }

    }


    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Movie>): CategoryRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { movieId ->
                moviesDatabase.categoryRemoteKeysDao().remoteKeysMovieId(movieId)
            }
        }
    }
}

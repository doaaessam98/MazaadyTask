package com.doaa.mazaadytask.data.paging

import android.content.ContentValues
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.doaa.mazaadytask.core.Utils.Constants
import com.doaa.mazaadytask.data.source.remote.api.MovieApiService
import com.doaa.mazaadytask.core.models.Movie

class MoviePaginationSearchSource (
    private val movieApiService: MovieApiService,
    private val query: String
) : PagingSource<Int, Movie>() {
    init {
        Log.e("MoviePaginationSearchSource", "PagingSource created for query: $query")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        Log.e("MoviePaginationSearchSource", "PagingSource created for query1: $query")

        Log.e("TAG", "doaa load: method ", )
       // Log.e(ContentValues.TAG, "loadmethod:${params.key}...........${params.loadSize} ", )

        val position = params.key ?: Constants.MOVIES_STARTING_PAGE_INDEX
        return try {
            Log.e("TAG", "loadmethod:${params.key}...........${params.loadSize} ", )

            val response = movieApiService.getMoviesSearch(query = query, page =position ,itemsPerPage = params.loadSize)
            val endOfPaginationReached = response.results.isEmpty()
            if (response.results.isNotEmpty()) {
                LoadResult.Page(
                    data = response.results,
                    prevKey = if (position == Constants.MOVIES_STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = if (endOfPaginationReached) null else position + 1
                )
            } else {
                //position + (params.loadSize / NETWORK_PAGE_SIZE)
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }



        } catch (e: Exception) {
            Log.e("TAG", "load: error", )
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}
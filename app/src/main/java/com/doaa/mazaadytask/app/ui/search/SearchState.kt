package com.doaa.mazaadytask.app.ui.search

import androidx.paging.PagingData
import com.doaa.mazaadytask.core.base.ViewState
import com.doaa.mazaadytask.core.models.Movie

import kotlinx.coroutines.flow.Flow

data class SearchState(
    val MoviesResult: PagingData<Movie>? = null,
    val searchQuery: String? = null,
    val isLoading: Boolean? = false
) : ViewState {
}
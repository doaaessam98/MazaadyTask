package com.doaa.mazaadytask.app.ui.home.view

import androidx.paging.PagingData
import com.doaa.mazaadytask.core.base.ViewState
import com.doaa.mazaadytask.core.models.Genre
import com.doaa.mazaadytask.core.models.Movie
import kotlinx.coroutines.flow.Flow

//data class HomeState {
//
//    data class Movies(val data:Flow<PagingData<Movie>>)
//    data class Error(val errorMessage: String) : HomeState()
//    object Idle :HomeState()
//    object Loading : HomeState()
//}

data class HomeState(
    val genresMovies: List<Genre> = emptyList(),
    val selectedGenreId: String? = null,
    val categoryMovies: PagingData<Movie>? = null,
    val isGridLayout: Boolean = false,
    val isLoading: Boolean?=true,
) : ViewState


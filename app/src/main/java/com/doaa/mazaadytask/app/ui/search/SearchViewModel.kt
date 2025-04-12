package com.doaa.mazaadytask.app.ui.search

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.doaa.mazaadytask.core.base.BaseViewModel
import com.doaa.mazaadytask.core.models.Movie
import com.doaa.mazaadytask.data.repository.movies.IRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: IRepository
) : BaseViewModel<SearchIntent, SearchState, SearchSideEffect>() {

    private val _pagingDataFlow = MutableStateFlow<Flow<PagingData<Movie>>?>(null)
    val pagingDataFlow: StateFlow<Flow<PagingData<Movie>>?> = _pagingDataFlow

    private val _searchBar = MutableStateFlow("")
    val searchBar: StateFlow<String> = _searchBar


    override fun initialState(): SearchState {
        return SearchState()
    }

    override fun handleEvents(event: SearchIntent) {
        when (event) {
            is SearchIntent.FetchMoviesForSearch -> {
                getSearchMovie(event.query)
            }

            is SearchIntent.MovieSelected -> {
                setEffect { SearchSideEffect.Navigation.OpenMovieDetails(movie = event.movie!!) }

            }

            is SearchIntent.BackToHome -> {
                setEffect { SearchSideEffect.Navigation.BackToHome }
            }

        }
    }


    private fun getSearchMovie(query: String) {

        setState { copy(isLoading = true) }
        viewModelScope.launch {
            val newPagingFlow = repository.getSearchMovies(query).cachedIn(viewModelScope)

            _pagingDataFlow.value = newPagingFlow

            setState { copy(isLoading = false) }
        }
    }
}






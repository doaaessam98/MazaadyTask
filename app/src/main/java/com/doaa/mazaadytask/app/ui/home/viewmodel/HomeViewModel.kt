package com.doaa.mazaadytask.app.ui.home.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.doaa.mazaadytask.app.ui.home.view.HomeIntent
import com.doaa.mazaadytask.app.ui.home.view.HomeSideEffect
import com.doaa.mazaadytask.app.ui.home.view.HomeState
import com.doaa.mazaadytask.core.base.BaseViewModel
import com.doaa.mazaadytask.core.base.ResponsResult
import com.doaa.mazaadytask.data.repository.movies.IRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: IRepository)
    : BaseViewModel<HomeIntent, HomeState, HomeSideEffect>() {


    init {
        setEvent(HomeIntent.FetchGenres)

    }

    override fun initialState(): HomeState {
        return HomeState()
    }

    override fun handleEvents(event: HomeIntent) {
        when (event) {
            is HomeIntent.FetchGenres -> {
                getMoviesGenres()
            }

            is HomeIntent.GetMoviesByGenre -> {
                getGenresMoviesById(event.genreId)
            }

            is HomeIntent.ToggleLayout -> {
                setState { copy(isGridLayout = !isGridLayout) }

            }

            is HomeIntent.updateFavouriteState -> {
                updateFavouriteState(event.movieId, event.isFav)

            }
            is HomeIntent.MovieSelected -> {
                setEffect { HomeSideEffect.Navigation.OpenMovieDetails(movie = event.movie!!) }

            }

            is HomeIntent.OpenSearchForMovie -> {
                setEffect { HomeSideEffect.Navigation.OpenSearch }
            }
        }

    }

    private fun updateFavouriteState(movieId: Int, isFav: Boolean) {
        viewModelScope.launch {
            repository.updateFavouriteState(movieId, isFav)
        }

    }

    private fun getMoviesGenres() {
        viewModelScope.launch {
            repository.getGenres().let {
                when (it) {
                    is ResponsResult.Loading -> {
                        setState { copy(isLoading = true) }
                    }

                    is ResponsResult.Success -> {

                        it.data?.collect { genres ->
                            setState { copy(genresMovies = genres, isLoading = false) }
                            getGenresMoviesById(genres[0].id)
                        }

                    }

                    is ResponsResult.Error -> {


                    }
                }
            }
        }
    }


    private fun getGenresMoviesById(categoryId: Int) {
        viewModelScope.launch {
            repository.getMoviesGenres(categoryId.toString()).cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    setState {
                        copy(
                            categoryMovies = pagingData,
                            selectedGenreId = categoryId.toString()
                        )
                    }
                }
        }
    }



}


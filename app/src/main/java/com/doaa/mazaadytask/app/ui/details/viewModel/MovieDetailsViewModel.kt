package com.doaa.mazaadytask.app.ui.details.viewModel

import androidx.lifecycle.viewModelScope
import com.doaa.mazaadytask.app.ui.details.view.DetailsIntent
import com.doaa.mazaadytask.app.ui.details.view.DetailsSideEffect
import com.doaa.mazaadytask.app.ui.details.view.DetailsState
import com.doaa.mazaadytask.core.base.BaseViewModel
import com.doaa.mazaadytask.core.base.ResponsResult
import com.doaa.mazaadytask.core.models.Movie
import com.doaa.mazaadytask.data.repository.details.DetailsIRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val detailsRepository: DetailsIRepository
):
    BaseViewModel<DetailsIntent, DetailsState, DetailsSideEffect>(){

    override fun initialState(): DetailsState {
       return DetailsState()
    }

    override fun handleEvents(event: DetailsIntent) {
          when(event){
              is DetailsIntent.AddMovieToFavourite ->{
                  addMoveToFavourite(event.movie)
              }
              is DetailsIntent.RemoveMovieToFavourite ->{
                  removeMovieFromFavourite(event.movieId)
              }
              is DetailsIntent.BackToHome ->{
                    setEffect { DetailsSideEffect.Navigation.Back }
              }
              is DetailsIntent.OpenMovieVideo ->{

              }
              is DetailsIntent.CheckIsFav ->{

              }
              is DetailsIntent.GetGenres ->{
                  getMovieGenres(event.genresIds)
              }
          }
    }

    private fun removeMovieFromFavourite(movieId: Int) {
        viewModelScope.launch {
            detailsRepository.removeFromFavourite(movieId).let {
                when(it){
                    is ResponsResult.Loading->{
                        setState { copy(loading=true) }
                    }
                    is ResponsResult.Success->{
                        setState { copy(isFav = false, loading = false) }
                        setEffect { DetailsSideEffect.ShowToast("movie removed from favourite") }

                    }
                    is ResponsResult.Error->{

                    }

                }
            }
        }
    }

    private fun addMoveToFavourite(movie: Movie) {
        viewModelScope.launch {
            detailsRepository.addToFavourite(movie.id).let {
                      when(it){
                          is ResponsResult.Loading->{
                              setState { copy(loading=true) }
                          }
                          is ResponsResult.Success->{
                              setState { copy(isFav = true, loading = false) }
                              setEffect { DetailsSideEffect.ShowToast("movie added to favourite") }

                          }
                          is ResponsResult.Error->{

                          }

                      }
           }
        }
    }


    private fun getMovieGenres(genreIds: List<Int>) {

        viewModelScope.launch {
             detailsRepository.getMovieGenreByIds(genreIds).let {
                 when(it){
                     is ResponsResult.Loading->{
                         setState { DetailsState(loading=true) }
                     }
                     is ResponsResult.Success->{
                         it.data?.collect{genres->
                             setState { copy(genresMovies=genres) }
                         }
                     }
                     is ResponsResult.Error->{

                     }

                 }
         }

         }
    }
}
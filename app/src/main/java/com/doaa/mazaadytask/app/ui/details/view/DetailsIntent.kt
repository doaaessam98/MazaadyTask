package com.doaa.mazaadytask.app.ui.details.view

import com.doaa.mazaadytask.core.base.ViewEvent
import com.doaa.mazaadytask.core.models.Movie


sealed class DetailsIntent : ViewEvent {
    data class   AddMovieToFavourite(val movie: Movie) : DetailsIntent()
    data class GetGenres(val genresIds: List<Int>): DetailsIntent()
    data class CheckIsFav(val id:Int): DetailsIntent()
    data class   RemoveMovieToFavourite(val movieId:Int) : DetailsIntent()
    sealed class   OpenMovieVideo(val movie: Movie) : DetailsIntent()
     object  BackToHome : DetailsIntent()
}
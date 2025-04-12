package com.doaa.mazaadytask.app.ui.home.view

import com.doaa.mazaadytask.core.base.ViewEvent
import com.doaa.mazaadytask.core.models.Movie


sealed class HomeIntent: ViewEvent {
    object FetchGenres: HomeIntent()
    object ToggleLayout : HomeIntent()
    data class  updateFavouriteState(val movieId: Int, val isFav: Boolean): HomeIntent()
    object OpenSearchForMovie: HomeIntent()
    data class MovieSelected(val movie: Movie?) : HomeIntent()
    data class GetMoviesByGenre(val genreId:Int): HomeIntent()

}

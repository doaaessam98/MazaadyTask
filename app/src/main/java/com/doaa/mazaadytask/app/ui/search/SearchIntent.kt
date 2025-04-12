package com.doaa.mazaadytask.app.ui.search

import com.doaa.mazaadytask.core.base.ViewEvent
import com.doaa.mazaadytask.core.models.Movie


sealed class SearchIntent: ViewEvent {
    data class FetchMoviesForSearch(val query:String) : SearchIntent()
    data class MovieSelected(val movie: Movie?) : SearchIntent()
    object BackToHome : SearchIntent()
}

package com.doaa.mazaadytask.app.ui.search

import com.doaa.mazaadytask.core.base.ViewSideEffect
import com.doaa.mazaadytask.core.models.Movie


sealed class SearchSideEffect : ViewSideEffect {
    data class ShowLoadDataError(val message:String): SearchSideEffect()
    sealed class Navigation : SearchSideEffect(){
        data class OpenMovieDetails(val movie: Movie): Navigation()
        object   BackToHome: Navigation()
    }
}
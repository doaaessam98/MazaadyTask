package com.doaa.mazaadytask.app.ui.details.view

import com.doaa.mazaadytask.core.base.ViewState
import com.doaa.mazaadytask.core.models.Genre


data class DetailsState(
    val genresMovies: List<Genre> = emptyList(),
    val loading:Boolean=false,
    val isFav:Boolean=false
): ViewState {
}
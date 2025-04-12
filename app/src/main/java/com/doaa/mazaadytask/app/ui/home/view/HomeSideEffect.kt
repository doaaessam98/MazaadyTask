package com.doaa.mazaadytask.app.ui.home.view

import com.doaa.mazaadytask.core.base.ViewSideEffect
import com.doaa.mazaadytask.core.models.Movie


sealed class HomeSideEffect : ViewSideEffect {
  data class ShowLoadDataError(val message:String): HomeSideEffect()
   sealed class Navigation : HomeSideEffect(){
      data class OpenMovieDetails(val movie: Movie): Navigation()
       object   OpenSearch: Navigation()
   }
}
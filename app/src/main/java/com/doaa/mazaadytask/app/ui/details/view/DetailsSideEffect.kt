package com.doaa.mazaadytask.app.ui.details.view

import com.doaa.mazaadytask.core.base.ViewSideEffect


sealed class DetailsSideEffect : ViewSideEffect {
    data class ShowToast(val message:String): DetailsSideEffect()

    sealed class Navigation : DetailsSideEffect(){
        object Back: Navigation()
    }
}
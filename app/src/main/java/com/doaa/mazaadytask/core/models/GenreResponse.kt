package com.doaa.mazaadytask.core.models


import com.google.gson.annotations.SerializedName


data class GenreResponse(
    @SerializedName("genres")
    val genres: List<Genre>
)
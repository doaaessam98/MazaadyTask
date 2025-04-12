package com.doaa.mazaadytask.data.source.remote

import com.doaa.mazaadytask.data.source.remote.api.MovieApiService
import com.doaa.mazaadytask.core.models.GenreResponse

interface IRemoteDataSource {

     val movieApiServiceObject: MovieApiService
     suspend fun getGenres(): GenreResponse
}




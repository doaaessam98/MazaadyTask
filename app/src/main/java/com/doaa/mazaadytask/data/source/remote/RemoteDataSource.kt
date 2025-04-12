package com.doaa.mazaadytask.data.source.remote


import com.doaa.mazaadytask.data.source.remote.api.MovieApiService
import com.doaa.mazaadytask.core.models.GenreResponse
import com.doaa.mazaadytask.data.source.remote.IRemoteDataSource
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val movieApiService: MovieApiService):
    IRemoteDataSource {


    override val movieApiServiceObject: MovieApiService
        get() = movieApiService

    override suspend fun getGenres(): GenreResponse {
        return movieApiService.getGenres()
    }
}
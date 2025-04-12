package com.doaa.mazaadytask.data

import com.doaa.mazaadytask.core.models.Genre
import com.doaa.mazaadytask.core.models.GenreResponse
import com.doaa.mazaadytask.data.source.local.db.MoviesDatabase
import com.doaa.mazaadytask.data.source.remote.IRemoteDataSource
import com.doaa.mazaadytask.data.source.remote.api.MovieApiService

class FakeRemoteDataSource (private val testGenres: List<Genre> = emptyList()): IRemoteDataSource {

    override val movieApiServiceObject: MovieApiService
        get() = throw UnsupportedOperationException("Not used in FakeLocalDataSource")

    val databaseObject: MoviesDatabase
        get() = throw UnsupportedOperationException("Not used in FakeLocalDataSource")


    override suspend fun getGenres(): GenreResponse {
        return GenreResponse(testGenres)
    }
}
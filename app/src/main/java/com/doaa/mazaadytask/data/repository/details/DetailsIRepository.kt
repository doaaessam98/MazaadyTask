package com.doaa.mazaadytask.data.repository.details

import com.doaa.mazaadytask.core.base.ResponsResult
import com.doaa.mazaadytask.core.models.Genre
import com.doaa.mazaadytask.core.models.Movie
import kotlinx.coroutines.flow.Flow

interface DetailsIRepository {

     suspend fun addToFavourite(id: Int): ResponsResult<Int>
     suspend fun removeFromFavourite(id: Int): ResponsResult<Int>
     suspend fun getMovieGenreByIds(genresIds: List<Int>):  ResponsResult<Flow<List<Genre>>>





}
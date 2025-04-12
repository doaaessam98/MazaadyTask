package com.doaa.mazaadytask.data.repository.details

import android.annotation.SuppressLint
import com.doaa.mazaadytask.core.base.ResponsResult
import com.doaa.mazaadytask.core.models.Genre
import com.doaa.mazaadytask.data.source.local.ILocalDataSource
import com.doaa.mazaadytask.data.source.remote.RemoteDataSource
import com.doaa.mazaadytask.core.models.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DetailsRepository  @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: ILocalDataSource

    ): DetailsIRepository {


    @SuppressLint("SuspiciousIndentation")
    override suspend fun addToFavourite(id: Int): ResponsResult<Int> {
         return try {
             val isAdded =  localDataSource.addToFavourite(id)
                 ResponsResult.Success(isAdded)

         }catch (e:Exception){
             ResponsResult.Error(e.message!!)
         }

    }

    override suspend fun removeFromFavourite(id: Int): ResponsResult<Int> {
        return try {
            val isRemoved =  localDataSource.removeFromFavourite(id)
            ResponsResult.Success(isRemoved)

        }catch (e:Exception){
            ResponsResult.Error(e.message!!)
        }
    }

    override suspend fun getMovieGenreByIds(genresIds: List<Int>):  ResponsResult<Flow<List<Genre>>> {
        return  try{
            val result = localDataSource.getMovieGenresByIds(genresIds)
            ResponsResult.Success(result)
        }catch (e:Exception){
            ResponsResult.Error(e.message!!)
        }
    }









}


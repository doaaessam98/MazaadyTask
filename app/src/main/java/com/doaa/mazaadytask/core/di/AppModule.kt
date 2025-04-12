package com.doaa.mazaadytask.core.di

import com.doaa.mazaadytask.data.repository.details.DetailsIRepository
import com.doaa.mazaadytask.data.repository.details.DetailsRepository
import com.doaa.mazaadytask.data.repository.movies.IRepository
import com.doaa.mazaadytask.data.repository.movies.Repository
import com.doaa.mazaadytask.data.source.local.ILocalDataSource
import com.doaa.mazaadytask.data.source.local.LocalDataSource
import com.doaa.mazaadytask.data.source.remote.IRemoteDataSource
import com.doaa.mazaadytask.data.source.remote.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface AppModule {

    @Binds
    fun provideLocalDataSource(localDataSource: LocalDataSource): ILocalDataSource

    @Binds
    fun provideRemoteDataSource(remoteDataSource: RemoteDataSource): IRemoteDataSource

    @Binds
    fun provideRepository(repository: Repository): IRepository

    @Binds
    fun provideFavouriteRepository(repository: DetailsRepository): DetailsIRepository


}
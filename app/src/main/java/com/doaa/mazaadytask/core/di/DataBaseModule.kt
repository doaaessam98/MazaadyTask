package com.doaa.mazaadytask.core.di

import android.content.Context
import androidx.room.Room
import com.doaa.mazaadytask.data.source.local.db.daos.CategoryRemoteKeysDao
import com.doaa.mazaadytask.data.source.local.db.MovieDao
import com.doaa.mazaadytask.data.source.local.db.MoviesDatabase
import com.doaa.mazaadytask.data.source.local.db.daos.GenreDao


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataBaseModule {

    @Provides
     @Singleton
     fun MovieDataBase(@ApplicationContext context: Context): MoviesDatabase =
         Room.databaseBuilder(context, MoviesDatabase::class.java,"movie_DB").build()

    @Provides
    @Singleton
    fun provideMovieDataBase(db: MoviesDatabase): MovieDao =db.movieDao()
    @Provides
    @Singleton
    fun provideGenreDataBase(db: MoviesDatabase): GenreDao =db.genreDao()


    @Provides
    @Singleton
    fun provideCategoryRemoteKeysDataBase(db: MoviesDatabase): CategoryRemoteKeysDao =db.categoryRemoteKeysDao()
}



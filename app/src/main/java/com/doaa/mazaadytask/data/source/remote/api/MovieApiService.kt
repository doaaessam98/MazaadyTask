package com.doaa.mazaadytask.data.source.remote.api

import com.doaa.mazaadytask.core.Utils.Constants
import com.doaa.mazaadytask.core.models.GenreResponse
import com.doaa.mazaadytask.core.models.Movie
import com.doaa.mazaadytask.core.models.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieApiService {
    @GET("movie/popular?")
    suspend fun getPopularMovies(
        @Query("api_key")api_key:String = Constants.API_KEY,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ):MovieResponse

    @GET("trending/movie/day?")
    suspend fun getTrendingMovies(
        @Query("api_key") api_key:String= Constants.API_KEY,
        @Query("page") page:Int,
        @Query("per_page") itemsPerPage: Int
    ): MovieResponse


    @GET("movie/upcoming?")
    suspend fun getUpcoming(
        @Query("api_key")api_key:String= Constants.API_KEY,
        @Query("page") page :Int,
        @Query("per_page") itemsPerPage: Int
    ): MovieResponse


    @GET("movie/{movie_id}?")
    suspend fun getMovieDetails(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String= Constants.API_KEY
    ): Response<Movie>


    @GET("search/movie?")
    suspend fun getMoviesSearch(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int,
        @Query("api_key") api_key: String = Constants.API_KEY
    ): MovieResponse



    @GET("genre/list?")
    suspend fun getGenres(
        @Query("api_key") api_key: String = Constants.API_KEY
    ): GenreResponse


    @GET("discover/movie?")
    suspend fun getMovieByGenreCategory(
        @Query("with_genres") with_genres: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int,
        @Query("api_key") api_key: String = Constants.API_KEY
    ):MovieResponse

}



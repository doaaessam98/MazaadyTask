package com.doaa.mazaadytask.data.source.local

import com.doaa.mazaadytask.data.source.local.db.MoviesDatabase
import com.doaa.mazaadytask.core.models.Genre
import com.doaa.mazaadytask.core.models.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeLocalDataSource : ILocalDataSource {


    private val favMovies = mutableListOf<Movie>()
    private val favMoviesFlow = MutableStateFlow<List<Movie>>(emptyList())

    private val genres = mutableListOf<Genre>()
    private val genresFlow = MutableStateFlow<List<Genre>>(emptyList())

    override val databaseObject: MoviesDatabase
        get() = throw UnsupportedOperationException("Not used in FakeLocalDataSource")

    override suspend fun addToFavourite(id: Int): Int {
        val movie = Movie(id = id, title = "Movie $id", isFav = true)
        if (favMovies.none { it.id == id }) {
            favMovies.add(movie)
            favMoviesFlow.value = favMovies.toList()
        }
        return 1
    }

    override suspend fun removeFromFavourite(id: Int): Int {
        val removed = favMovies.removeIf { it.id == id }
        favMoviesFlow.value = favMovies.toList()
        return if (removed) 1 else 0
    }

    override fun getFavouriteMovie(): Flow<List<Movie>> {
        return favMoviesFlow
    }



    override fun insertAllGenres(genres: List<Genre>) {
        this.genres.clear()
        this.genres.addAll(genres)
        genresFlow.value = this.genres.toList()
    }

    override fun getGenres(): Flow<List<Genre>> {
        return genresFlow
    }

    override fun getMovieGenresByIds(genres: List<Int>): Flow<List<Genre>> {
        return genresFlow.map { all ->
            all.filter { genres.contains(it.id) }
        }
    }
}

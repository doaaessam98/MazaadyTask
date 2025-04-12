package com.doaa.mazaadytask.data

import androidx.test.filters.MediumTest
import com.doaa.mazaadytask.core.base.ResponsResult
import com.doaa.mazaadytask.core.models.Genre
import com.doaa.mazaadytask.data.repository.movies.Repository
import com.doaa.mazaadytask.data.source.local.FakeLocalDataSource
import com.doaa.mazaadytask.data.repository.movies.IRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


@MediumTest
class MoviesGenreRepositoryTesting {

    private lateinit var localDataSource: FakeLocalDataSource
    private lateinit var remoteDataSource: FakeRemoteDataSource

    lateinit var repository: IRepository

    @Before
    fun initDatabase(){

        localDataSource = FakeLocalDataSource()
         var genres: List<Genre> = listOf(Genre(1, "Action"),Genre(2, "Anime"))

        remoteDataSource= FakeRemoteDataSource(genres)
        repository= Repository(remoteDataSource,localDataSource)
    }


    @Test
    fun getGenres_returnsSuccessWithData() = runTest {
        val result = repository.getGenres()
        assert(result is ResponsResult.Success)

        val genresFlow = (result as ResponsResult.Success).data
        val genres = genresFlow?.first()

        assertEquals(2, genres?.size)
        assertEquals("Action", genres?.first()?.name)
    }
    @Test
    fun updateFavouriteState_whenIsFavTrue_addsMovieToFavourites() = runTest {
        val movieId = 10

        val result = repository.updateFavouriteState(movieId, true)

        assert(result is ResponsResult.Success)
        val favList = localDataSource.getFavouriteMovie().first()
        assertEquals(1, favList.size)
        assertEquals(movieId, favList[0].id)
        assertEquals(true, favList[0].isFav)
    }


}
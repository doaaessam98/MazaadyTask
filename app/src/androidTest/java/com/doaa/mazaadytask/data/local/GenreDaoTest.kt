package com.doaa.mazaadytask.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.doaa.mazaadytask.core.models.Genre
import com.doaa.mazaadytask.data.source.local.db.MoviesDatabase
import com.doaa.mazaadytask.data.source.local.db.daos.GenreDao
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class GenreDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: MoviesDatabase
    private lateinit var genreDao: GenreDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MoviesDatabase::class.java
        ).allowMainThreadQueries().build()

        genreDao = database.genreDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAll_and_getGenres_returnsSortedGenres() = runBlocking {
        val genres = listOf(
            Genre(2, "Comedy"),
            Genre(1, "Action")
        )

        genreDao.insertAll(genres)

        val result = genreDao.getGenres().first()

        assertEquals(2, result.size)
        assertEquals("Action", result[0].name)
        assertEquals("Comedy", result[1].name)
    }

    @Test
    fun getMoviesByIds_returnsFilteredGenres() = runBlocking {
        val genres = listOf(
            Genre(1, "Action"),
            Genre(2, "Drama"),
            Genre(3, "Horror")
        )
        genreDao.insertAll(genres)

        val filtered = genreDao.getMoviesByIds(listOf(1, 3)).first()

        assertEquals(2, filtered.size)
        assertEquals("Action", filtered[0].name)
        assertEquals("Horror", filtered[1].name)
    }
}

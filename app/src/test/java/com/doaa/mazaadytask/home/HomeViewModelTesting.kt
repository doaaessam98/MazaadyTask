package com.doaa.mazaadytask.home

import androidx.test.filters.MediumTest
import com.doaa.mazaadytask.MainDispatcherRule
import com.doaa.mazaadytask.app.ui.home.view.HomeIntent
import com.doaa.mazaadytask.app.ui.home.viewmodel.HomeViewModel
import com.doaa.mazaadytask.data.FakeRepository
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@MediumTest
class HomeViewModelTesting {
    private lateinit var viewModel: HomeViewModel
    private lateinit var repository: FakeRepository
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun init() {
        repository = FakeRepository()
        viewModel =
            HomeViewModel(repository)
    }

    @Test
    fun toggleLayout_changes_isGridLayout() = runTest {
        assertFalse(viewModel.viewState.value.isGridLayout)

        viewModel.setEvent(HomeIntent.ToggleLayout)

        assertTrue(viewModel.viewState.value.isGridLayout)
    }
    @Test
    fun fetchGenres_onInit_setsGenresAndCategoryMoviesInState()= runTest {
        val state = viewModel.viewState.value

        assertEquals(2, state.genresMovies.size)
        assertEquals("Action", state.genresMovies[0].name)
    }

    @Test
    fun updateFavouriteState_whenIsFavTrue_addsMovieToFavourites() = runTest {
        viewModel.setEvent(HomeIntent.updateFavouriteState(10, true))
        advanceUntilIdle()

        assertEquals(1, repository.updatedFavorites.size)
        assertEquals(Pair(10, true), repository.updatedFavorites[0])



    }

}
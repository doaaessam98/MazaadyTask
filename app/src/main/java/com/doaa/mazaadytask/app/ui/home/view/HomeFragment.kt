package com.doaa.mazaadytask.app.ui.home.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.map
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.doaa.mazaadytask.R
import com.doaa.mazaadytask.app.ui.home.view.adapter.MovieLoadStateAdapter
import com.doaa.mazaadytask.app.ui.home.view.adapter.MoviesAdapter
import com.doaa.mazaadytask.app.ui.home.view.adapter.MoviesGenresAdapter
import com.doaa.mazaadytask.app.ui.home.viewmodel.HomeViewModel
import com.doaa.mazaadytask.core.models.Genre
import com.doaa.mazaadytask.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    lateinit var adapter: MoviesGenresAdapter
    lateinit var movieAdapter: MoviesAdapter
    private lateinit var gridLayoutManager: GridLayoutManager



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpGenresRecyclerView()
        setUpMovieRecyclerView()
        handelOnClickListeners()
        lifecycleScope.launchWhenStarted {
            viewModel.viewState.collect { state ->
                renderState(state)
            }



        }

        handelSideEffects()
    }


    private fun handelOnClickListeners() {
        binding.ivGridButton.setOnClickListener {
            viewModel.setEvent(HomeIntent.ToggleLayout)

        }
        binding.cvSearch.setOnClickListener{
            viewModel.setEvent(HomeIntent.OpenSearchForMovie)
        }


    }

    private fun handelSideEffects() {
        lifecycleScope.launchWhenStarted {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is HomeSideEffect.Navigation.OpenMovieDetails -> {
                        val action = HomeFragmentDirections
                            .actionHomeFragmentToDetailsFragment(effect.movie)
                        findNavController().navigate(action)

                    }

                    HomeSideEffect.Navigation.OpenSearch -> {
                     findNavController().navigate(R.id.searchFragment)
                    }

                    is HomeSideEffect.ShowLoadDataError -> {
                        Snackbar.make(binding.root, effect.message, Snackbar.LENGTH_LONG).show()

                    }
                }
            }
        }

    }


    private fun renderState(state: HomeState) {
        when {
            state.isLoading == true -> {
                binding.progressBar.isVisible
            }

            else -> {
                setGenersData(state.genresMovies)
                setMoviesData(state)



            }

        }

    }

    private fun setMoviesData(state: HomeState) {
        state.categoryMovies?.let {
            movieAdapter.submitData(lifecycle, it)
            binding.movieRecycler.scrollToPosition(0)
            gridLayoutManager.spanCount = if (state.isGridLayout) 2 else 1
            movieAdapter.notifyItemRangeChanged(0, movieAdapter.itemCount)
            val iconRes = if (state.isGridLayout) R.drawable._ic_list_view else R.drawable.ic_grid_view
            binding.ivGridButton.setImageResource(iconRes)
        }
    }

    private fun setGenersData(genresMovies: List<Genre>) {
        adapter.submitList(genresMovies)

    }


    private fun showErrorUI(message: String) {

    }

    private fun showLoadingUI() {
        Log.e("TAG", "showLoadingUI: ")
    }


    private fun setUpGenresRecyclerView() {
        adapter = MoviesGenresAdapter(
            onGenreSelected = { genreId ->
                viewModel.setEvent(HomeIntent.GetMoviesByGenre(genreId))
            }
        )
        binding.categoryRecycler.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        binding.categoryRecycler.adapter = adapter

    }

    private fun setUpMovieRecyclerView() {
        movieAdapter = MoviesAdapter(
            onMovieSelected = { movieId ->
                viewModel.setEvent(HomeIntent.MovieSelected(movieId))
            },
            onFavClicked = { movieId, isFav ->
                viewModel.setEvent(HomeIntent.updateFavouriteState(movieId, isFav))

            }
        )
        gridLayoutManager = GridLayoutManager(requireContext(), 1)
        binding.movieRecycler.layoutManager = gridLayoutManager

        binding.movieRecycler.adapter = movieAdapter.withLoadStateFooter(
            footer = MovieLoadStateAdapter { movieAdapter.retry() }
        )

        lifecycleScope.launch {
            movieAdapter.loadStateFlow.collectLatest { loadStates ->
                binding.progressBar.isVisible = loadStates.refresh is LoadState.Loading
                binding.movieRecycler.isVisible = loadStates.refresh is LoadState.NotLoading
                binding.layoutError.isVisible = loadStates.refresh is LoadState.Error
                val appendError = loadStates.append as? LoadState.Error
                appendError?.let {
                    Toast.makeText(
                        requireContext(),
                        "Load more error: ${it.error.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                val isEmptyList = loadStates.refresh is LoadState.NotLoading &&
                        movieAdapter.itemCount == 0
                binding.layoutEmpty.isVisible = isEmptyList
            }
        }
        binding.retryButton.setOnClickListener {
            movieAdapter.retry()
        }


    }


}
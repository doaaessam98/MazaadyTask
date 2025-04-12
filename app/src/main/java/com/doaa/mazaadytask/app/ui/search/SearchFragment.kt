package com.doaa.mazaadytask.app.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.doaa.mazaadytask.R
import com.doaa.mazaadytask.app.ui.search.view.adapter.SearchMoviesAdapter
import com.doaa.mazaadytask.databinding.FragmentSearchBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()

    lateinit var adapter: SearchMoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    viewModel.setEvent(SearchIntent.FetchMoviesForSearch(query))
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return true
            }
        })
    }


    private fun handelSideEffects() {
        lifecycleScope.launchWhenStarted {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is SearchSideEffect.Navigation.OpenMovieDetails -> {
                        val action = SearchFragmentDirections
                            .actionSearchFragmentToDetailsFragment(effect.movie)
                        findNavController().navigate(action)

                    }

                    SearchSideEffect.Navigation.BackToHome -> {
                        findNavController().navigateUp()

                    }

                    is SearchSideEffect.ShowLoadDataError -> {
                        Snackbar.make(binding.root, effect.message, Snackbar.LENGTH_LONG).show()

                    }
                }
            }
        }

    }


    private fun renderState(state: SearchState) {
        state.isLoading?.let {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
                binding.animationView.visibility = View.GONE

            } else {
                binding.progressBar.visibility = View.GONE

            }
        }
        setMoviesData()


    }

    private fun setMoviesData() {
        lifecycleScope.launch {
            viewModel.pagingDataFlow.collectLatest { flow ->
                flow?.let {
                    it.collectLatest { pagingData ->
                        binding.animationView.visibility = View.GONE
                        adapter.submitData(pagingData)
                    }
                }
            }

        }
    }


    private fun setUpMovieRecyclerView() {
        adapter = SearchMoviesAdapter(
            onMovieSelected = { movieId ->
                viewModel.setEvent(SearchIntent.MovieSelected(movieId))
            },

            )
        binding.movieRecycler.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.movieRecycler.adapter = adapter


    }


}
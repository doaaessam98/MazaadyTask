package com.doaa.mazaadytask.app.ui.details.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.doaa.mazaadytask.R
import com.doaa.mazaadytask.app.ui.details.viewModel.MovieDetailsViewModel
import com.doaa.mazaadytask.app.ui.home.view.HomeIntent
import com.doaa.mazaadytask.app.ui.home.view.HomeState
import com.doaa.mazaadytask.app.ui.home.view.adapter.MoviesAdapter
import com.doaa.mazaadytask.app.ui.home.view.adapter.MoviesGenresAdapter
import com.doaa.mazaadytask.app.ui.home.viewmodel.HomeViewModel
import com.doaa.mazaadytask.core.Utils.Constants
import com.doaa.mazaadytask.core.models.Movie
import com.doaa.mazaadytask.databinding.FragmentDetailsBinding
import com.doaa.mazaadytask.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: MovieDetailsViewModel by viewModels()

    lateinit var adapter: MovieGenresAdapter
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie = args.moveData
        viewModel.setEvent(DetailsIntent.GetGenres(movie.genreIds))
        bindingDataToViews(movie)
        setUpGenresRecyclerView()
        handelOnClickListeners(movie)
        lifecycleScope.launchWhenStarted {
            viewModel.viewState.collect { state ->
                renderState(state)
            }


        }
    }

    private fun handelOnClickListeners(movie: Movie) {
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.ivFav.setOnClickListener {
            if (movie.isFav) {
                viewModel.setEvent(DetailsIntent.RemoveMovieToFavourite(movie.id))
                movie.isFav = false
            } else {
                viewModel.setEvent(DetailsIntent.AddMovieToFavourite(movie))
                movie.isFav = true

            }
        }
    }

    private fun bindingDataToViews(movie: Movie) {
        val imageUrl = "${Constants.IMAGE_URL}${movie?.posterPath}"
        binding.posterImage.load(imageUrl) {
            crossfade(true)
            listener(onStart = {
                binding.progressBar.visibility = View.VISIBLE
            }, onSuccess = { _, _ ->
                binding.progressBar.visibility = View.GONE
            }, onError = { _, _ ->
                binding.progressBar.visibility = View.GONE
            })
        }
        binding.tvRating.text = movie.voteAverage.toString()
        binding.tvVotes.text = movie.voteCount.toString()
        binding.title.text = movie.title
        binding.tvOverview.text = movie.overview


    }

    private fun renderState(state: DetailsState) {
        adapter.submitList(state.genresMovies)
        state.isFav.let {
            binding.ivFav.setImageResource(
                if (it) R.drawable.ic_fav_fill else R.drawable.ic_border_fav
            )


        }
    }


    private fun setUpGenresRecyclerView() {
        adapter = MovieGenresAdapter()
        binding.rvGenres.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )

        binding.rvGenres.adapter = adapter

    }


}
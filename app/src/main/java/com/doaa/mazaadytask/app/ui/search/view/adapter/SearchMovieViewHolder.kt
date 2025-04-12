package com.doaa.mazaadytask.app.ui.search.view.adapter

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.doaa.mazaadytask.R
import com.doaa.mazaadytask.core.Utils.Constants
import com.doaa.mazaadytask.core.models.Movie
import com.doaa.mazaadytask.databinding.MovieItemBinding

class SearchMovieViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie?,onMovieSelected: (Movie)->Unit) {
        binding.ivFav.visibility = View.GONE
        binding.movieName.text = movie?.title
        binding.releaseDate.text = movie?.releaseDate
        val imageUrl = "${Constants.IMAGE_URL}${movie?.posterPath}"
        binding.movieImg.load(imageUrl) {
            crossfade(true)
            listener(
                onStart = {
                    binding.progressBar.visibility = View.VISIBLE
                },
                onSuccess = { _, _ ->
                    binding.progressBar.visibility = View.GONE
                },
                onError = { _, _ ->
                    binding.progressBar.visibility = View.GONE
                }
            )
        }

       itemView.setOnClickListener{
           onMovieSelected(movie!!)
       }
    }

}
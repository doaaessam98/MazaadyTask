package com.doaa.mazaadytask.app.ui.home.view.adapter

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.doaa.mazaadytask.R
import com.doaa.mazaadytask.core.Utils.Constants
import com.doaa.mazaadytask.core.models.Genre
import com.doaa.mazaadytask.core.models.Movie
import com.doaa.mazaadytask.databinding.CategoryItemBinding
import com.doaa.mazaadytask.databinding.FragmentHomeBinding
import com.doaa.mazaadytask.databinding.MovieItemBinding

class MovieViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie?, onFavClicked: (Int, Boolean)->Unit,onMovieSelected: (Movie)->Unit) {
        binding.movieName.text = movie?.title
        binding.releaseDate.text = movie?.releaseDate
        val imageUrl = "${Constants.IMAGE_URL}${movie?.posterPath}"
        binding.movieImg.load(imageUrl) {
            crossfade(true)
//            placeholder(R.drawable.placeholder)
//            error(R.drawable.error_image)
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
        if (movie != null) {
            Log.e("TAG", "bind: ${movie.isFav}", )
            binding.ivFav.setImageResource(
                if (movie.isFav) R.drawable.ic_fav_fill else R.drawable.ic_border_fav
            )
        }

        binding.ivFav.setOnClickListener{
            onFavClicked(movie?.id!!,!movie.isFav)
        }
       itemView.setOnClickListener{
           onMovieSelected(movie!!)
       }
    }

}
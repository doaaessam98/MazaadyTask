package com.doaa.mazaadytask.app.ui.home.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.doaa.mazaadytask.core.models.Movie
import com.doaa.mazaadytask.databinding.MovieItemBinding

class MoviesAdapter(val onMovieSelected:(Movie)->Unit,val onFavClicked:(Int,Boolean)->Unit)  : PagingDataAdapter<Movie, MovieViewHolder>(MOVIE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        movie?.let {
            holder.bind(it,onFavClicked = { movieId, isFav,  ->
                onFavClicked(movieId, isFav)
                notifyItemChanged(position)
            },
                onMovieSelected = {
                    onMovieSelected(it)
                })
        }
    }


    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return (oldItem.id == newItem.id )
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }
}
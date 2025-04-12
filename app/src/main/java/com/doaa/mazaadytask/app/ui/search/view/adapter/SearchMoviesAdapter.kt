package com.doaa.mazaadytask.app.ui.search.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.doaa.mazaadytask.core.models.Movie
import com.doaa.mazaadytask.databinding.MovieItemBinding

class SearchMoviesAdapter(val onMovieSelected:(Movie)->Unit)  : PagingDataAdapter<Movie, SearchMovieViewHolder>(MOVIE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMovieViewHolder {
        val view = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchMovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchMovieViewHolder, position: Int) {
        Log.e("TAG", "onBindViewHolder: ", )
        val movie = getItem(position)
        movie?.let {
            holder.bind(it,
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
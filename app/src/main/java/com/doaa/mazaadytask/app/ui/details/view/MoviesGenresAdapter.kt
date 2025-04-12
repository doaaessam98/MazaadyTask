package com.doaa.mazaadytask.app.ui.details.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doaa.mazaadytask.core.models.Genre
import com.doaa.mazaadytask.databinding.CategoryItemBinding

class MovieGenresAdapter:ListAdapter<Genre, MovieGenresAdapter.MovieGenresViewHolder>(MOVIE_GENRES_COMPARATOR) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieGenresViewHolder {
        val view = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieGenresViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieGenresViewHolder, position: Int) {
        val genre = getItem(position)
        holder.bind(genre)

    }

    class MovieGenresViewHolder(val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: Genre?) {
            binding.categoryName.text = genre?.name }

    }


    companion object {
        private val MOVIE_GENRES_COMPARATOR = object : DiffUtil.ItemCallback<Genre>() {
            override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
                return (oldItem.id == newItem.id)
            }

            override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean =
                oldItem == newItem
        }
    }
}
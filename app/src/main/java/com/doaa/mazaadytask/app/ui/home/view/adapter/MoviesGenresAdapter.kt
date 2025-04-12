package com.doaa.mazaadytask.app.ui.home.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.doaa.mazaadytask.core.models.Genre
import com.doaa.mazaadytask.databinding.CategoryItemBinding

class MoviesGenresAdapter (val onGenreSelected : (Int)-> Unit):ListAdapter<Genre, MovieGenresViewHolder>(MOVIE_GENRES_COMPARATOR) {

    private var selectedPosition = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieGenresViewHolder {
        val view = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieGenresViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieGenresViewHolder, position: Int) {
        val genre = getItem(position)
        holder.bind(genre, position == selectedPosition)

        holder.itemView.setOnClickListener {
            val previousPosition = selectedPosition
            selectedPosition = holder.bindingAdapterPosition
            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)
            onGenreSelected(genre.id)
        }
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
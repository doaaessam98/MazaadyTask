package com.doaa.mazaadytask.app.ui.home.view.adapter

import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.doaa.mazaadytask.R
import com.doaa.mazaadytask.core.models.Genre
import com.doaa.mazaadytask.core.models.Movie
import com.doaa.mazaadytask.databinding.CategoryItemBinding
import com.doaa.mazaadytask.databinding.FragmentHomeBinding

class MovieGenresViewHolder(val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(genre: Genre?, isSelected: Boolean) {
        binding.categoryName.text = genre?.name
        val colorRes = if (isSelected) R.color.purple_200 else android.R.color.transparent
        val textColor = if (isSelected) R.color.white else android.R.color.black

    val color = ContextCompat.getColor(itemView.context, colorRes)
        binding.categoryName.setBackgroundColor(color)
        binding.categoryName.setTextColor(itemView.context.getColor(textColor))


    }

}
package com.doaa.mazaadytask.app.ui.home.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doaa.mazaadytask.databinding.ItemLoadStateBinding

class MovieLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<MovieLoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): MovieLoadStateViewHolder {
        val view = ItemLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieLoadStateViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState, retry)
    }
}

class MovieLoadStateViewHolder(val binding: ItemLoadStateBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(loadState: LoadState, retry: () -> Unit) {
        if (loadState is LoadState.Error) {
            binding.loadStateErrorMessage.text = loadState.error.localizedMessage
        }

        binding.loadStateProgress.isVisible = loadState is LoadState.Loading
        binding.loadStateRetry.isVisible = loadState is LoadState.Error
        binding.loadStateErrorMessage.isVisible = loadState is LoadState.Error

        binding.loadStateRetry.setOnClickListener { retry.invoke() }
    }
}

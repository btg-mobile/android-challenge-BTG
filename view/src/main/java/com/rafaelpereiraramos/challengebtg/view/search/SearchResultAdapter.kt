package com.rafaelpereiraramos.challengebtg.view.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.rafaelpereiraramos.challengebtg.repository.model.Movie
import com.rafaelpereiraramos.challengebtg.view.R
import com.rafaelpereiraramos.challengebtg.view.utils.GlideRequests

class SearchResultAdapter(
    private val glide: GlideRequests
) : PagedListAdapter<Movie, MovieCardViewHolder>(DIFF_STRATEGY) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCardViewHolder {
        return MovieCardViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_movie_item, parent, false),
            glide
        )
    }

    override fun onBindViewHolder(holder: MovieCardViewHolder, position: Int) {
        holder.bindMovie(getItem(position))
    }

    companion object {
        val DIFF_STRATEGY = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
}
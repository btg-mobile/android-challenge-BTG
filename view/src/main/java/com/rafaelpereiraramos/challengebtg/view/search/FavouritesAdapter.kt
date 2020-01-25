package com.rafaelpereiraramos.challengebtg.view.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rafaelpereiraramos.challengebtg.repository.model.Movie
import com.rafaelpereiraramos.challengebtg.view.R
import com.rafaelpereiraramos.challengebtg.view.utils.GlideRequests

class FavouritesAdapter(
    private val glide: GlideRequests
) : RecyclerView.Adapter<MovieCardViewHolder>() {

    private val movies = mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCardViewHolder {
        return MovieCardViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_movie_item, parent, false),
            glide
        )
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieCardViewHolder, position: Int)  = holder.bindMovie(movies[position])

    fun updateFavourites(list: List<Movie>) {
        movies.clear()
        movies.addAll(list)
        notifyDataSetChanged()
    }
}
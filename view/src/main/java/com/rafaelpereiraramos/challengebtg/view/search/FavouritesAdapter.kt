package com.rafaelpereiraramos.challengebtg.view.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.rafaelpereiraramos.challengebtg.repository.model.Movie
import com.rafaelpereiraramos.challengebtg.view.R
import com.rafaelpereiraramos.challengebtg.view.utils.GlideRequests

class FavouritesAdapter(
    private val glide: GlideRequests
) : RecyclerView.Adapter<MovieCardViewHolder>(), Filterable {
    private val movies = mutableListOf<Movie>()
    private val filteredMovies = mutableListOf<Movie>()
    private val filter = FavouritesMovieFilter(movies, ::applyFilter)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCardViewHolder {
        return MovieCardViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_movie_item, parent, false),
            glide
        )
    }

    override fun getItemCount(): Int = filteredMovies.size

    override fun onBindViewHolder(holder: MovieCardViewHolder, position: Int)  = holder.bindMovie(filteredMovies[position])

    override fun getFilter(): Filter = filter

    fun updateFavourites(list: List<Movie>) {
        movies.clear()
        filteredMovies.clear()

        movies.addAll(list)
        filteredMovies.addAll(list)

        notifyDataSetChanged()
    }

    private fun applyFilter(list: List<Movie>) {
        filteredMovies.clear()
        filteredMovies.addAll(list)
        notifyDataSetChanged()
    }
}
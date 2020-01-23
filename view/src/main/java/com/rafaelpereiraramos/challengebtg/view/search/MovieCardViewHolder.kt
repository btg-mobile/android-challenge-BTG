package com.rafaelpereiraramos.challengebtg.view.search

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rafaelpereiraramos.challengebtg.repository.model.Movie
import kotlinx.android.synthetic.main.card_movie_item.view.*

class MovieCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val title: TextView = itemView.prompt_title

    fun bindMovie(movie: Movie?) {
        if (movie == null)
            return

        title.text = movie.id.toString()
    }
}
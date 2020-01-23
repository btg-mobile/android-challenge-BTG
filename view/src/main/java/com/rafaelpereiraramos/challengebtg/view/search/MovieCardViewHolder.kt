package com.rafaelpereiraramos.challengebtg.view.search

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rafaelpereiraramos.challengebtg.repository.model.Movie
import com.rafaelpereiraramos.challengebtg.view.utils.GlideRequests
import kotlinx.android.synthetic.main.card_movie_item.view.*

class MovieCardViewHolder(itemView: View, private val glide: GlideRequests) : RecyclerView.ViewHolder(itemView) {

    private val title: TextView = itemView.prompt_title
    private val cover: ImageView = itemView.prompt_img_cover

    fun bindMovie(movie: Movie?) {
        if (movie == null)
            return

        title.text = movie.title

        glide
            .load("https://image.tmdb.org/t/p/w154/${movie.coverUrl}")
            .error(android.R.drawable.stat_notify_error)
            .into(cover)
    }
}
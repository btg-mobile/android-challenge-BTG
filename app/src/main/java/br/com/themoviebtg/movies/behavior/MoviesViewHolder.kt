package br.com.themoviebtg.movies.behavior

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.themoviebtg.R
import br.com.themoviebtg.movies.model.MovieModel

class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tvMovie = this.itemView.findViewById<TextView>(R.id.tv_movie_item_title)

    fun bind(movieModel: MovieModel) {
        this.tvMovie.text = movieModel.original_title

    }
}
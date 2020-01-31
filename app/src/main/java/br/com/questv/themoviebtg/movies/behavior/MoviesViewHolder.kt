package br.com.questv.themoviebtg.movies.behavior

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.questv.themoviebtg.R
import br.com.questv.themoviebtg.movies.model.MovieModel

class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tvMovie = this.itemView.findViewById<TextView>(R.id.tv_movie)

    fun bind(movieModel: MovieModel) {
        this.tvMovie.text = movieModel.name

    }
}
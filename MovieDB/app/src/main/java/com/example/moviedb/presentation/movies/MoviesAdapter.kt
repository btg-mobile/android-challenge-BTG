package com.example.moviedb.presentation.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.R
import com.example.moviedb.data.model.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie.view.*

class MoviesAdapter(
    private val movies: List<Movie>,
    private val onItemClickListener: ((movie: Movie) -> Unit)
) : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MoviesViewHolder(view, onItemClickListener)
    }

    override fun getItemCount() = movies.count()

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bindView(movies[position])

    }

    class MoviesViewHolder(
        view: View,
        private val onItemClickListener: ((movie: Movie) -> Unit)
    ) : RecyclerView.ViewHolder(view) {


        private val title = view.textTitle
        private val year = view.textYear
        private val poster = view.posterImage
        fun bindView(movie: Movie) {

            Picasso.get().load("http://image.tmdb.org/t/p/w780" + movie.posterPath).into(poster)

            title.text = movie.title
            year.text = movie.year

            itemView.setOnClickListener {
                onItemClickListener.invoke(movie)
            }

        }
    }
}
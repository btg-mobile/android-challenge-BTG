package com.brunoalmeida.movies.presentation.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brunoalmeida.movies.R
import com.brunoalmeida.movies.data.model.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_item.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import android.content.Context
import com.brunoalmeida.movies.BuildConfig


class MoviesAdapter(
    private val movies: List<Movie>,
    private val onItemClickListener: ((movie: Movie) -> Unit)
) : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MoviesViewHolder(itemView, onItemClickListener)
    }

    override fun getItemCount() = movies.count()

    override fun onBindViewHolder(viewHolder: MoviesViewHolder, position: Int) {
        viewHolder.bindView(movies[position])
    }

    class MoviesViewHolder(
        itemView: View,
        private val onItemClickListener: ((movie: Movie) -> Unit)
    ) :
        RecyclerView.ViewHolder(itemView) {
        private val title = itemView.textTitle
        private val releaseDate = itemView.textReleaseDate

        fun bindView(movie: Movie) {
            title.text = movie.title
            releaseDate.text = movie.releaseDate
            Picasso.get().load(BuildConfig.URL_POSTER + movie.posterPath).into(itemView.moviePoster)
            itemView.setOnClickListener {
                onItemClickListener.invoke(movie)
            }
        }
    }

}
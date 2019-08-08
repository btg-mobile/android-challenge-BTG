package com.arturkida.popularmovies_kotlin.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arturkida.popularmovies_kotlin.BuildConfig
import com.arturkida.popularmovies_kotlin.R
import com.arturkida.popularmovies_kotlin.model.Movie
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_movie_info.view.*

class MoviesListAdapter(private val context: Context?,
                        private var movies: List<Movie>)
    : RecyclerView.Adapter<MoviesListAdapter.MoviesListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie_info, parent, false)

        return MoviesListViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MoviesListViewHolder, position: Int) = holder.bind(movies[position])

    fun updateMovies(movies: List<Movie>) {
        if (!movies.isNullOrEmpty()) {
            this.movies = movies
            notifyDataSetChanged()
        }
    }

    inner class MoviesListViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {

        fun bind(movie: Movie) {
            setMovieTitle(movie)
            setMovieDate(movie)
            setMoviePoster(movie)
        }

        private fun setMovieDate(movie: Movie) {
            itemView.tv_movie_year.text = movie.release_date
        }

        private fun setMovieTitle(movie: Movie) {
            itemView.tv_movie_title.text = movie.title
        }

        private fun setMoviePoster(movie: Movie) {
            val posterPath = BuildConfig.BASE_IMAGE_URL + movie.poster_path

            Glide.with(context)
                .load(posterPath)
                .into(itemView.iv_movie_poster)
        }
    }
}
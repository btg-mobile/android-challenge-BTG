package com.uchoa.btg.movie.ui.movielist

import android.content.Context
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.uchoa.btg.movie.R
import com.uchoa.btg.movie.callbacks.OnMoviesClickCallback
import com.uchoa.btg.movie.models.Movie
import com.uchoa.btg.movie.utils.Constants
import com.uchoa.btg.movie.utils.FontStyleUtil
import com.uchoa.btg.movie.utils.imageUtils.GlideManager


class MoviesAdapter(
    private var movies: MutableList<Movie>,
    private var callback: OnMoviesClickCallback
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    private var context: Context? = null
    private var moviesDisplayed: MutableList<Movie> = movies

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(moviesDisplayed[position])
    }

    override fun getItemCount(): Int {
        return moviesDisplayed.size
    }

    fun appendMovies(moviesToAppend: List<Movie>) {
        movies.addAll(moviesToAppend)
        moviesDisplayed = movies
        notifyDataSetChanged()
    }

    fun filterMovies(query: String) {
        if (query.isEmpty()) {
            moviesDisplayed = movies
        } else {

            val moviesFound: MutableList<Movie> = mutableListOf()

            for (movie in movies) {

                val year = movie.releaseDate?.split("-")?.get(0)

                if (movie.title!!.toLowerCase().contains(query.toLowerCase()) ||
                    year!!.toLowerCase().contains(query.toLowerCase())
                ) {
                    moviesFound.add(movie)
                }
            }

            moviesDisplayed = moviesFound
        }

        notifyDataSetChanged()
    }

    fun notifyFavoriteStatusChanges(id: Int) {
        val intent = Intent(MoviesFragment.UPDATE_FAVORITE_MESSAGE)
        intent.putExtra(MoviesFragment.MOVIE_ID, id)
        LocalBroadcastManager.getInstance(context!!).sendBroadcast(intent)
    }

    fun updateMovies(movieUpdatedId: Int) {
        for (movie in moviesDisplayed) {
            if (movie.id == movieUpdatedId) {
                movie.favorite = !movie.favorite
            }
        }
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var mainLayout: CardView = itemView.findViewById(R.id.movieMainContainer)
        private var releaseDate: TextView = itemView.findViewById(R.id.item_movie_release_date)
        private var title: TextView = itemView.findViewById(R.id.item_movie_title)
        private var poster: ImageView = itemView.findViewById(R.id.item_movie_poster)
        private var rate: ImageView = itemView.findViewById(R.id.item_movie_rate)

        fun bind(movie: Movie) {
            title.text = movie.title
            releaseDate.text =
                    itemView.context.getString(R.string.movie_list_year).plus(movie.releaseDate?.split("-")?.get(0))

            if (movie.favorite) {
                rate.setImageResource(R.drawable.ic_rate_on)
            } else {
                rate.setImageResource(R.drawable.ic_rate_off)
            }

            itemView.setOnClickListener {
                callback.onCellClick(title, rate, movie)
            }
            rate.setOnClickListener {
                callback.onRateClick(movie)
                notifyFavoriteStatusChanges(movie.id)
            }


            FontStyleUtil.applyFontStyle(context!!, title, Constants.FONT_STYLE_COMIC_SANS)
            FontStyleUtil.applyFontStyle(context!!, releaseDate, Constants.FONT_STYLE_COMIC_SANS)

            GlideManager.loadImage(itemView.context, poster, movie.posterPath!!)
        }
    }
}
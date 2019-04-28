package com.example.desafiobtg.usecases.movielist

import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.desafiobtg.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieListAdapter constructor(private val mPresenter: MovieListContract.Presenter) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(view: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(view.context).inflate(R.layout.item_movie, view, false)
        return MovieViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mPresenter.getMovieCount()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as? MovieViewHolder)?.let { movieHolder ->

            mPresenter.bindMovieHolder(movieHolder as? MovieListContract.MovieHolder, position)
            movieHolder.favoriteIV?.setOnClickListener {
                val isFavorite = !it.isSelected
                movieHolder.setMovieFavorited(isFavorite)
                mPresenter.setMovieFavorite(position, isFavorite)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            when (payloads[0]) {
                Payload.FAVORITE_CHANGED -> {
                    mPresenter.bindFavoriteIcon(holder as? MovieListContract.MovieHolder, position)
                }
            }
        }

        super.onBindViewHolder(holder, position, payloads)
    }

    object Payload {
        val FAVORITE_CHANGED = "favorite_changed"
    }

    inner class MovieViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView), MovieListContract.MovieHolder {

        private val posterIV: AppCompatImageView? = itemView.iv_poster
        private val titleTV: AppCompatTextView? = itemView.tv_title
        private val yearTV: AppCompatTextView? = itemView.tv_year
        val favoriteIV: AppCompatImageView? = itemView.iv_favorite

        override fun setMoviePoster(posterUrl: String) {
            posterIV?.let { imageView ->
                Picasso.get()
                    .load(posterUrl)
                    .into(imageView)
            }
        }

        override fun setMovieTitle(movieTitle: String) {
            titleTV?.text = movieTitle
        }

        override fun setMovieYear(movieYear: String) {
            yearTV?.text = movieYear
        }

        override fun setMovieFavorited(isFavorited: Boolean) {
            favoriteIV?.isSelected = isFavorited
        }
    }
}
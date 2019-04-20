package com.example.leopaim.themoviedb.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.leopaim.themoviedb.BuildConfig.URL_POSTER
import com.example.leopaim.themoviedb.model.Movie
import org.jetbrains.anko.*
import com.example.leopaim.themoviedb.R
import com.squareup.picasso.Picasso
import org.jetbrains.anko.sdk25.coroutines.onClick

class MainAdapter(private val result: List<Movie>, private val dbMovie: List<Movie>, private val posterListener: (Movie) -> Unit, private val favoriteListener: (Movie, ImageView) -> Unit)
    : RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MovieViewHolder {
        return MovieViewHolder(MovieUi().createView(AnkoContext.create(p0.context, p0)))
    }

    override fun getItemCount(): Int = result.size

    override fun getItemViewType(position: Int): Int { return position }

    override fun onBindViewHolder(p0: MovieViewHolder, p1: Int) {
        p0.bindItem(result[p1], dbMovie.contains(result[p1]), posterListener,favoriteListener)
    }
}

class  MovieUi : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return  LayoutInflater.from(ui.ctx).inflate(R.layout.movie_item, ui.owner, false)
    }
}

class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view){

    private val moviePoster: ImageView = view.find(R.id.imageMovie)
    private val movieTitle: TextView = view.find(R.id.textMovieTitle)
    private val favoriteButton: ImageView = view.find(R.id.favoriteImage)


    fun bindItem(movies: Movie, favorite: Boolean, posterListener: (Movie) ->  Unit, favoriteListener: (Movie, ImageView) ->  Unit) {


        Picasso.get().load(URL_POSTER + movies.poster).into(moviePoster)
        if(movies.poster == null){
            moviePoster.setImageResource(R.drawable.not_found)
        }
        movieTitle.text = movies.title + " ("+ movies.getYear() +")"
        if(favorite)
            favoriteButton.setImageResource(R.drawable.favorite)
        else
            favoriteButton.setImageResource(R.drawable.unfavorite)


        moviePoster.onClick{
            posterListener(movies)
        }

        favoriteButton.onClick {
            favoriteListener(movies, favoriteButton)
        }

    }






}
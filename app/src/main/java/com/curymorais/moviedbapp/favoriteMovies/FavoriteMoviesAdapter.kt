package com.curymorais.moviedbapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_row_movie.*


class FavoriteMoviesAdapter : RecyclerView.Adapter<FavoriteMoviesAdapter.MovieHolder>() {

    private var movieList : ArrayList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_row_movie, parent, false))
    }

    override fun getItemCount(): Int {
       return movieList.size
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bind(movieList[position])
    }

    class MovieHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(movieDetail: String){

            movie_name.text = movieDetail
        }
    }

    fun updateList(movieList: ArrayList<String>){
        this.movieList = movieList
    }
}
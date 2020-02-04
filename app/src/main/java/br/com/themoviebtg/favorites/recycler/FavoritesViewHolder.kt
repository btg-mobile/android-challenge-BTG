package br.com.themoviebtg.favorites.recycler

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.themoviebtg.R
import br.com.themoviebtg.movies.model.MovieModel

class FavoritesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {


    fun bind(movieModel: MovieModel) {
        val textView = this.itemView.findViewById<TextView>(R.id.tv_movie_item_title)
        textView.text = movieModel.name
    }

}
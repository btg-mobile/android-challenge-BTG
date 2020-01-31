package br.com.questv.themoviebtg.favorites.recycler

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.questv.themoviebtg.R
import br.com.questv.themoviebtg.movies.model.MovieModel

class FavoritesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {


    fun bind(movieModel: MovieModel) {
        val textView = this.itemView.findViewById<TextView>(R.id.tv_favorite)
        textView.text = movieModel.name
    }

}
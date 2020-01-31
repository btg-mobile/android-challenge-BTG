package br.com.questv.themoviebtg.movies.behavior

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.questv.themoviebtg.R
import br.com.questv.themoviebtg.movies.model.GenreModel

class GenresViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {


    fun bind(genreModel: GenreModel) {
        this.itemView.findViewById<TextView>(R.id.tv_genre).text = genreModel.name
    }
}
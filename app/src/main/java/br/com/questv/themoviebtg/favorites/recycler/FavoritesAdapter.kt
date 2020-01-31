package br.com.questv.themoviebtg.favorites.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.questv.themoviebtg.R
import br.com.questv.themoviebtg.movies.model.MovieModel


class FavoritesAdapter(private val favoritesList: List<MovieModel>) :
    RecyclerView.Adapter<FavoritesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflateView(inflater, parent)
        return FavoritesViewHolder(view)
    }

    private fun inflateView(inflater: LayoutInflater, viewGroup: ViewGroup): View {
        return inflater.inflate(R.layout.rv_item_favorite, viewGroup, false)
    }

    override fun getItemCount() = this.favoritesList.size

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(this.favoritesList[position])
    }
}
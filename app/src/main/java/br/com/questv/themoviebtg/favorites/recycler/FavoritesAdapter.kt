package br.com.questv.themoviebtg.favorites.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import br.com.questv.themoviebtg.R
import br.com.questv.themoviebtg.movies.model.MovieModel


class FavoritesAdapter(private val favoritesList: List<MovieModel>) : BaseAdapter() {

    private fun inflateView(inflater: LayoutInflater, viewGroup: ViewGroup): View {
        return inflater.inflate(R.layout.gv_item_favorite, viewGroup, false)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(parent!!.context)
        val view = this.inflateView(inflater, parent)

        val textView = view.findViewById<TextView>(R.id.tv_favorite)
        textView.text = "Tests"

        return view
    }

    override fun getItem(position: Int) = this.favoritesList[position]

    override fun getItemId(position: Int) = this.favoritesList[position].longId

    override fun getCount() = this.favoritesList.size
}
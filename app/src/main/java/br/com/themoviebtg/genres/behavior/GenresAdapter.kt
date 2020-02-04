package br.com.themoviebtg.genres.behavior

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.questv.themoviebtg.R
import br.com.themoviebtg.genres.model.GenreModel

class GenresAdapter(private val genres: List<GenreModel>) :
    RecyclerView.Adapter<GenresViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = inflateView(parent, layoutInflater)
        return GenresViewHolder(view!!)
    }

    private fun inflateView(viewGroup: ViewGroup, layoutInflater: LayoutInflater?) =
        layoutInflater?.inflate(R.layout.rv_item_genre, viewGroup, false)

    override fun getItemCount() = this.genres.size

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        holder.bind(this.genres[position])
    }
}
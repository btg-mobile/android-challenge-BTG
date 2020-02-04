package br.com.themoviebtg.movies.behavior

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.themoviebtg.R
import br.com.themoviebtg.movies.model.MovieModel

class MoviesAdapter(private val movieModelList: List<MovieModel>) :
    RecyclerView.Adapter<MoviesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = this.inflateView(inflater, parent)
        return MoviesViewHolder(view)
    }

    private fun inflateView(inflater: LayoutInflater, viewGroup: ViewGroup) =
        inflater.inflate(R.layout.rv_item_movie, viewGroup, false)


    override fun getItemCount() = movieModelList.size


    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(this.movieModelList[position])
    }
}
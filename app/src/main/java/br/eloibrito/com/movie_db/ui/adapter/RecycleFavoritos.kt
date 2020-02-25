package br.eloibrito.com.movie_db.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.eloibrito.com.movie_db.R
import br.eloibrito.com.movie_db.data.network.EndPoint
import br.eloibrito.com.movie_db.databinding.RecycleFavoritosBinding
import br.eloibrito.com.movie_db.models.Movies
import br.eloibrito.com.movie_db.ui.RecycleViewItemClick
import br.eloibrito.com.movie_db.utils.CarregaImagemDaUrl
import java.util.*

class RecycleFavoritos(private var movies: List<Movies>, val listner : RecycleViewItemClick) :
    RecyclerView.Adapter<RecycleFavoritos.ViewHolder>(), Filterable {

    internal var lista_temp: List<Movies>? = null

    class ViewHolder(val recycleBiding : RecycleFavoritosBinding) : RecyclerView.ViewHolder(recycleBiding.root)

    init {
        lista_temp = movies
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int) =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.recycle_favoritos,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.recycleBiding.filmes = movies[position]

        CarregaImagemDaUrl.carregaImagen(
            holder.recycleBiding.cView.context,
            holder.recycleBiding.imagens,
            String.format("%s/%s", EndPoint.image_movie, movies[position].poster_path)
        )

        holder.recycleBiding.cView.setOnClickListener {
            listner.onClickMovies(holder.recycleBiding.cView, movies[position], position)
        }

        holder.recycleBiding.like.setOnClickListener {
            listner.onClickMoviesLike(movies[position], position, !movies[position].like)
        }

    }


    override fun getItemCount() = movies.size


    override fun getFilter(): Filter {
        return object : Filter() {

            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                try {
                    if (charSequence != null && charSequence.isNotEmpty()) {
                        val charString = charSequence.toString().toLowerCase()
                        val filtrado = ArrayList<Movies>()
                        for (movies in lista_temp!!)
                            if (movies.original_title!!.toLowerCase().contains(charString) || movies.title!!.toLowerCase().contains(
                                    charString
                                ) || movies.release_date!!.toLowerCase().contains(
                                    charString
                                )
                            )
                                filtrado.add(movies)


                        filterResults.count = filtrado.size
                        filterResults.values = filtrado
                    } else {
                        filterResults.count = lista_temp!!.size
                        filterResults.values = lista_temp
                    }

                } catch (err: Exception) {
                    err.printStackTrace()
                }

                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                try {
                    movies = filterResults.values as ArrayList<Movies>
                    notifyDataSetChanged()
                } catch (err: Exception) {
                    err.printStackTrace()
                }

            }
        }
    }
}
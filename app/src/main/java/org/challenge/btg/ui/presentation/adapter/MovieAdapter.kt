package org.challenge.btg.ui.presentation.adapter

import android.content.Intent
import android.graphics.Typeface
import android.graphics.Typeface.BOLD
import android.graphics.Typeface.NORMAL
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_movie.view.*
import org.challenge.btg.R
import org.challenge.btg.data.model.Movie
import org.challenge.btg.data.modelView.MovieModelView
import org.challenge.btg.ui.presentation.DetailActivity
import org.challenge.btg.utils.NetworkCachedTask

class MovieAdapter(private var movies: List<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var filterStr = ""
    private var originalMovies: List<Movie> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount() = movies.count()

    override fun onBindViewHolder(viewHolder: MovieViewHolder, position: Int) {
        viewHolder.bindView(movies[position])
        viewHolder.itemView.setOnClickListener{ view ->
            val intent = Intent(view.context, DetailActivity::class.java).apply {
                putExtra(R.string.PARAM_DETAIL.toString(), movies[position].id)
            }
            view.context.startActivity(intent)
        }
    }

    fun updateListMovies() {
        movies = MovieModelView.getPopular()
        originalMovies = movies
        processFilter()
    }

    fun moreListMovies() {
        movies = MovieModelView.getPopular(true)
        originalMovies = movies
        processFilter()
    }

    fun updateListFavorites() {
        movies = MovieModelView.getFavorite()
        originalMovies = movies
        processFilter()
    }

    fun setFilter(filter: String) {
        filterStr = filter
        processFilter()
    }

    fun getPage() = MovieModelView.getPage()+1

    private fun processFilter() {
        movies = if (filterStr.isNotEmpty())
            movies.filter { filterStr.toLowerCase() in it.title.toLowerCase() || filterStr in it.year }
        else
            originalMovies
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private val imageCover = itemView.image_poster
        private val textTitle = itemView.text_title
        private val textYear = itemView.text_year
        private val buttonFavorite = itemView.button_favorite

        fun bindView(movie: Movie){
            //var hash = movie.hashCode()
            //itemView.setBackgroundColor(Color.rgb( hash / 6, hash/4,hash/3))
            textTitle.text = movie.title
            textTitle.setTypeface(Typeface.DEFAULT, if (movie.favorite) BOLD else NORMAL)
            textYear.text = "Ano: ${movie.year}"
            textYear.setTypeface(Typeface.DEFAULT, if (movie.favorite) BOLD else NORMAL)
            buttonFavorite.setChecked(movie.favorite)
            NetworkCachedTask(imageCover).execute(movie.poster, movie.id)
        }
    }
}
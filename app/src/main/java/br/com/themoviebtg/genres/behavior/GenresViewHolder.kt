package br.com.themoviebtg.genres.behavior

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.themoviebtg.R
import br.com.themoviebtg.genres.model.GenreModel
import br.com.themoviebtg.movies.behavior.MoviesAdapter
import br.com.themoviebtg.movies.behavior.MoviesPresenter
import br.com.themoviebtg.movies.behavior.MoviesView
import br.com.themoviebtg.movies.model.MovieModel

class GenresViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    MoviesView {


    private val moviesRecyclerView: RecyclerView = itemView.findViewById(R.id.rv_movies)
    private val progressBarMovies: ProgressBar = itemView.findViewById(R.id.pb_movies)
    private val moviePresenter =
        MoviesPresenter(this)


    init {
        this.moviesRecyclerView.layoutManager = LinearLayoutManager(
            itemView.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    fun bind(genreModel: GenreModel) {
        this.itemView.findViewById<TextView>(R.id.tv_genre).text = genreModel.name
        this.moviePresenter.fetchMoviesByGenre(genreModel.name)
    }

    override fun showProgress() {
        this.progressBarMovies.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        this.progressBarMovies.visibility = View.GONE
    }

    override fun initRecyclerView(movieModelList: List<MovieModel>) {
        this.moviesRecyclerView.adapter =
            MoviesAdapter(movieModelList)
    }

    override fun showErrorMessage(errorMessage: String) {
        println(errorMessage)
    }


}
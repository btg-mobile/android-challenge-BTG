package br.com.themoviebtg.movies.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.GridView
import androidx.fragment.app.Fragment
import br.com.themoviebtg.R
import br.com.themoviebtg.movie.MovieActivity
import br.com.themoviebtg.movies.behavior.MoviesAdapter
import br.com.themoviebtg.movies.behavior.MoviesPresenter
import br.com.themoviebtg.movies.behavior.MoviesView
import br.com.themoviebtg.movies.model.MovieModel
import kotlinx.android.synthetic.main.fragment_movies.*


class MoviesFragment : Fragment(),
    MoviesView {
    private lateinit var gridView: GridView
    private val presenter =
        MoviesPresenter(this)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_movies, container, false)!!
        this.gridView = view.findViewById(R.id.gv_movies)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.presenter.fetchPopularMovies()
    }

    override fun showProgress() {
        pb_movies.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        pb_movies.visibility = View.GONE
    }

    override fun initGridView(moviesAdapter: MoviesAdapter) {
        this.gridView.adapter = moviesAdapter
        attachGridOnClickListener()

    }

    private fun attachGridOnClickListener() {
        gridView.onItemClickListener = OnItemClickListener { parent, _, position, _ ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            println(selectedItem)
            navigateToMovieDetails(parent.getItemAtPosition(position) as MovieModel)
        }
    }

    override fun showErrorMessage(errorMessage: String) {
        println(errorMessage)
    }

    override fun navigateToMovieDetails(movieModel: MovieModel) {
        val intent = Intent(context, MovieActivity::class.java)
        intent.putExtra("movie_model", movieModel)
        activity?.startActivity(intent)
    }

}
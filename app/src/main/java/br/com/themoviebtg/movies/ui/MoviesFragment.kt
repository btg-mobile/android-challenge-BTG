package br.com.themoviebtg.movies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.themoviebtg.R
import br.com.themoviebtg.movies.behavior.MoviesAdapter
import br.com.themoviebtg.movies.behavior.MoviesPresenter
import br.com.themoviebtg.movies.behavior.MoviesView
import kotlinx.android.synthetic.main.fragment_movies.*

class MoviesFragment : Fragment(),
    MoviesView {
    private lateinit var recyclerView: GridView
    private val presenter =
        MoviesPresenter(this)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_movies, container, false)!!
        this.recyclerView = view.findViewById(R.id.gv_movies)
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
        this.recyclerView.adapter = moviesAdapter

    }

    override fun showErrorMessage(errorMessage: String) {
        println(errorMessage)
    }

}
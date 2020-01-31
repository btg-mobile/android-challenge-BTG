package br.com.questv.themoviebtg.movies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.questv.themoviebtg.R
import br.com.questv.themoviebtg.movies.behavior.GenresAdapter
import br.com.questv.themoviebtg.movies.behavior.MoviesPresenter
import kotlinx.android.synthetic.main.fragment_movies.*

class MoviesFragment : Fragment(),
    MoviesView {
    private lateinit var recyclerView: RecyclerView
    private val presenter = MoviesPresenter(this)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_movies, container, false)!!
        this.recyclerView = view.findViewById(R.id.rv_genres)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.presenter.fetchAllGenres()
    }

    override fun initRecyclerView(genresAdapter: GenresAdapter) {
        this.recyclerView.layoutManager = LinearLayoutManager(context)
        this.recyclerView.adapter = genresAdapter
    }

    override fun showProgress() {
        pb_genres.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        pb_genres.visibility = View.GONE
    }

}
package com.curymorais.moviedbapp.favoriteMovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.curymorais.moviedbapp.FavoriteMoviesAdapter
import com.curymorais.moviedbapp.R
import kotlinx.android.synthetic.main.content_main.*

class FavoriteMoviesFragment : Fragment() {

    val TAG = FavoriteMoviesFragment::class.java.simpleName
    lateinit var presenter: FavoriteMoviesPresenter
    lateinit var adapter: FavoriteMoviesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_popular_movies, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FavoriteMoviesAdapter()
        movie_list.adapter = adapter
        movie_list.setHasFixedSize(true)
        movie_list.layoutManager = LinearLayoutManager(activity)

        presenter = FavoriteMoviesPresenter(this)
        updateData()
    }

    fun updateView(movieList: ArrayList<String>?) {
        adapter.updateList(movieList!!)
        adapter.notifyDataSetChanged()
    }

    fun updateData() {
        presenter.getData()
    }

}


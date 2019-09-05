package com.curymorais.moviedbapp.popularMovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.curymorais.moviedbapp.Contract
import com.curymorais.moviedbapp.PopularMoviesAdapter
import com.curymorais.moviedbapp.R
import com.curymorais.moviedbapp.data.domain.Movie
import com.curymorais.twitchtop100.PopularMoviesInteractor
import com.curymorais.twitchtop100.PopularMoviesPresenter
import kotlinx.android.synthetic.main.content_main.*

class PopularMoviesFragment : Fragment() , Contract.BaseView{

    val TAG = PopularMoviesFragment::class.java.simpleName
    lateinit var presenter: Contract.BasePresenter
    lateinit var adapter: PopularMoviesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_popular_movies, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PopularMoviesAdapter()
        movie_list.adapter = adapter
        movie_list.setHasFixedSize(true)
        movie_list.layoutManager = LinearLayoutManager(activity)

        presenter = PopularMoviesPresenter(this, PopularMoviesInteractor())
        updateData()
    }

    override fun updateData() {
        presenter.getData()
    }

    override fun updateView(movieList: ArrayList<Movie>?) {
        adapter.updateList(movieList!!)
        adapter.notifyDataSetChanged()
    }

    override fun getDataSuccess() {

    }

    override fun getDataFail(msg: String?) {

    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }

}


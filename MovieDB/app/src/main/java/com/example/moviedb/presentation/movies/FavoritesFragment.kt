package com.example.moviedb.presentation.movies


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.moviedb.R
import com.example.moviedb.presentation.details.MovieDetailsActivity
import com.example.moviedb.presentation.repository.DataRepository
import kotlinx.android.synthetic.main.fragment_movies.*


class FavoritesFragment : Fragment() {

    private lateinit var mHandler: Handler
    private lateinit var mRunnable:Runnable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onStart() {
        super.onStart()

        //Load the view
        mHandler = Handler()
        swiperMovies.isRefreshing = true
        mRunnable = Runnable {
            swiperMovies.isRefreshing = false
        }
        updateView()

        swiperMovies.setOnRefreshListener {
            updateView()
            mRunnable = Runnable {
                swiperMovies.isRefreshing = false
            }
        }


    }

    fun updateView(){
        val movies = DataRepository.getFavoriteMovies()

        if(!movies.isEmpty()) {
            with(recyclerMovies) {
                layoutManager =
                    LinearLayoutManager(
                        context,
                        RecyclerView.VERTICAL,
                        false
                    )
                setHasFixedSize(true)
                adapter = MoviesAdapter(movies) { movie ->
                    val intent = MovieDetailsActivity.getStartIntent(
                        context,
                        movie.id,
                        movie.title,
                        movie.overview,
                        movie.posterPath,
                        movie.voteAverage
                    )
                    this.context.startActivity(intent)
                }
            }
        }

    }


}

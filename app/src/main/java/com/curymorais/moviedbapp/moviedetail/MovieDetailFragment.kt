package com.curymorais.moviedbapp.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.curymorais.moviedbapp.BuildConfig
import com.curymorais.moviedbapp.R
import com.curymorais.moviedbapp.popularMovies.PopularMoviesFragment
import com.curymorais.moviedbapp.data.domain.Movie
import com.curymorais.moviedbapp.data.repository.local.MoviePreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_movie_detail.*

class MovieDetailFragment(movieDetail: Movie) : Fragment(), View.OnClickListener {

    val TAG = PopularMoviesFragment::class.java.simpleName
    var movieDetail = movieDetail
    var movieDetailPresenter : MovieDetailPresenter? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_movie_detail, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_back.setOnClickListener(this)
        button_movie_detail_fav.setOnClickListener(this)

        movieDetailPresenter = MovieDetailPresenter(this, movieDetail)

        if(movieDetailPresenter!!.isMovieFavorited()){
            button_movie_detail_fav.isChecked = true
        }

        Picasso.get().load(BuildConfig.BASE_LOGO + movieDetail.posterPath).into(movie_detail_image)
        movie_detail_name.text = movieDetail.title
        movie_detail_description.text = movieDetail.overview
        movie_detail_score.text = movieDetail.voteCount.toString()
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            button_movie_detail_fav.id -> movieDetailPresenter!!.favoriteMovie()
            button_back.id -> activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_container, PopularMoviesFragment())?.addToBackStack(null)?.commit()
        }
    }
}
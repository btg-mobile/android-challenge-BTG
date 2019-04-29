package com.example.desafiobtg.usecases.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.desafiobtg.R
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.frag_movie_details.*
import javax.inject.Inject

class MovieDetailsFragment @Inject constructor(): DaggerFragment(), MovieDetailsContract.View {

    companion object {
        const val MOVIE_ID_KEY = "movie_id_key"
    }

    @Inject
    lateinit var mPresenter: MovieDetailsContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.frag_movie_details, container, false)
        mPresenter.takeView(this)
        return view
    }

    override fun onDestroy() {
        mPresenter.dropView()
        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.takeArguments(arguments, this)
        setListeners()
    }

    private fun setListeners() {
        iv_favorite?.setOnClickListener {
            val isFavorite = !it.isSelected
            setMovieFavorite(isFavorite)
            mPresenter.setMovieFavorite(isFavorite)
        }
    }

    override fun setMoviePoster(url: String) {
        iv_poster?.let { imageView ->
            Picasso.get()
                .load(url)
                .into(imageView)
        }
    }

    override fun setMovieBackdrop(url: String) {
        iv_backdrop_bg?.let { imageView ->
            Picasso.get()
                .load(url)
                .into(imageView)
        }
    }

    override fun setMovieTitle(title: String) {
        tv_title?.text = title
    }

    override fun setMovieYear(year: String) {
        tv_year?.text = year
    }

    override fun setMovieFavorite(isFavorite: Boolean) {
        iv_favorite?.isSelected = isFavorite
    }

    override fun setOverviewText(text: String?) {
        text?.let {
            tv_overview?.text = text
        } ?: run {
            tv_overview_title?.visibility = View.GONE
            tv_overview?.visibility = View.GONE
        }
    }

    override fun showGenreList(genreList: List<String>) {
        var text = ""
        genreList.forEach { genre ->
            text = "$text$genre\n"
        }
        tv_genres?.text = text
    }
}
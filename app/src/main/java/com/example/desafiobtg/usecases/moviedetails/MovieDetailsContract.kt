package com.example.desafiobtg.usecases.moviedetails

import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.desafiobtg.di.qualifiers.utils.BasePresenter
import com.example.desafiobtg.di.qualifiers.utils.BaseView

interface MovieDetailsContract {
    interface View: BaseView<Presenter> {
        fun setMoviePoster(url: String)
        fun setMovieTitle(title: String)
        fun setMovieYear(year: String)
        fun setMovieFavorite(isFavorite: Boolean)
        fun setMovieBackdrop(url: String)
        fun setOverviewText(text: String?)
        fun showGenreList(genreList: List<String>)
    }

    interface Presenter: BasePresenter<View> {
        fun takeArguments(arguments: Bundle?, fragment: Fragment)
        fun setMovieFavorite(isFavorite: Boolean)
    }
}
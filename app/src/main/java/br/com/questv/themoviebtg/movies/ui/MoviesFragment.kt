package br.com.questv.themoviebtg.movies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.questv.themoviebtg.R
import br.com.questv.themoviebtg.movies.behavior.MoviesPresenter

class MoviesFragment : Fragment(),
    MoviesView {
    private val presenter =
        MoviesPresenter(this)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_movies, container, false)!!

}
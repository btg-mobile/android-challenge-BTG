package br.com.themoviebtg.movie

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.com.questv.themoviebtg.R
import kotlinx.android.synthetic.main.activity_movie.*

class MovieActivity : AppCompatActivity(), MovieView {

    private val presenter = MoviePresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        this.presenter.fetchMovie("movieId")

    }

    override fun showProgress() {
        pb_movie.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        pb_movie.visibility = View.GONE
    }


}
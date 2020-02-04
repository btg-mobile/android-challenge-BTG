package br.com.themoviebtg.movie

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import br.com.themoviebtg.R
import br.com.themoviebtg.movies.model.MovieModel
import com.nostra13.universalimageloader.core.ImageLoader
import kotlinx.android.synthetic.main.activity_movie.*

class MovieActivity : AppCompatActivity(), MovieView {

    private val presenter = MoviePresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        this.presenter.initView(intent.getSerializableExtra("movie_model") as MovieModel)
    }

    override fun showProgress() {
        pb_movie.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        pb_movie.visibility = View.GONE
    }

    override fun attachMovieOverview(overview: String) {
        tv_movie_overview.text = overview
    }

    override fun attachMovieBackground(posterUrl: String) {
        val imageLoader = ImageLoader.getInstance()
        imageLoader.displayImage(posterUrl, iv_movie_promo)
        iv_movie_promo.scaleType = ImageView.ScaleType.CENTER_CROP
    }

    override fun attachMovieGenres(genres: List<String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun attachMovieTitle(title: String?) {
        tv_movie_title.text = title
    }


}
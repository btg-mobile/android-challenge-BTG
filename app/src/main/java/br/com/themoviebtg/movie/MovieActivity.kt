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

    private lateinit var movieModel: MovieModel
    private val presenter = MoviePresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        movieModel = intent.getSerializableExtra("movie_model") as MovieModel
        this.presenter.initView(movieModel)
        this.initView()
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

    override fun initView() {
        updateFavoriteImage()
        ib_favorite_movie.setOnClickListener { swipeFavorite() }
        rb_movie_rating.rating = this.movieModel.vote_average / 2
        tv_movie_vote_average.text = this.movieModel.vote_average.toString()
    }

    private fun updateFavoriteImage() {
        ib_favorite_movie.setImageResource(
            when (this.movieModel.isFavorite) {
                true -> R.drawable.ic_favorite_red_32dp
                false -> R.drawable.ic_favorite_border_white_32dp
            }
        )
    }

    private fun swipeFavorite() {
        movieModel.isFavorite = !(movieModel.isFavorite)
        updateFavoriteImage()
    }


}
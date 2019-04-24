package com.uchoa.btg.movie.ui.moviedetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.uchoa.btg.movie.R
import com.uchoa.btg.movie.models.Movie
import com.uchoa.btg.movie.models.Trailer
import com.uchoa.btg.movie.ui.movielist.MoviesFragment
import com.uchoa.btg.movie.utils.Constants
import com.uchoa.btg.movie.utils.FontStyleUtil
import com.uchoa.btg.movie.utils.imageUtils.GlideManager
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity(), MovieDetailContract.View {

    private var title = ""
    private var movieId = 1
    private var favorite= false

    private lateinit var presenter: MovieDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        movieId = intent.getIntExtra(MOVIE_ID, 0)
        title = intent.getStringExtra(MOVIE_TITLE)
        favorite = intent.getBooleanExtra(MOVIE_FAVORITE, false)

        presenter = MovieDetailPresenter(applicationContext, this)

        setupToolbar()
        setUpFontStyle()

        loadMovieDetails()
    }

    private fun setUpFontStyle() {
        FontStyleUtil.applyFontStyle(this, toolbarTitle, Constants.FONT_STYLE_COMIC_SANS)
        FontStyleUtil.applyFontStyle(this, movieErrorMessage, Constants.FONT_STYLE_COMIC_SANS)
        FontStyleUtil.applyFontStyle(this, movieDetailsTitle, Constants.FONT_STYLE_COMIC_SANS)
        FontStyleUtil.applyFontStyle(this, movieDetailsGenres, Constants.FONT_STYLE_COMIC_SANS)
        FontStyleUtil.applyFontStyle(this, movieDetailsReleaseDate, Constants.FONT_STYLE_COMIC_SANS)
        FontStyleUtil.applyFontStyle(this, movieSummaryLabel, Constants.FONT_STYLE_COMIC_SANS)
        FontStyleUtil.applyFontStyle(this, movieDetailsOverview, Constants.FONT_STYLE_COMIC_SANS)
        FontStyleUtil.applyFontStyle(this, movieTrailersLabel, Constants.FONT_STYLE_COMIC_SANS)
    }

    override fun onResume() {
        super.onResume()
        progress.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onBackPressed() {
        toolbarTitle.setTextColor(resources.getColor(R.color.defaultColor))
        supportFinishAfterTransition()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                toolbarTitle.setTextColor(resources.getColor(R.color.defaultColor))
                supportFinishAfterTransition()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupToolbar() {
        toolbarTitle.text = title
        setSupportActionBar(toolbar)

        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowTitleEnabled(false)
        }

        if (favorite) {
            movieDetailsRate.setImageResource(R.drawable.ic_rate_on)
        } else {
            movieDetailsRate.setImageResource(R.drawable.ic_rate_off)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun loadMovieDetails() {
        progress.visibility = View.VISIBLE
        presenter.getMovie(movieId)
    }

    private fun setRateClickListener(movie: Movie) {
        movieDetailsRate.setOnClickListener {
            if (movie.favorite) {
                favorite = false
                movie.favorite = false
                presenter.deleteFavoriteMovie(movieId)
                movieDetailsRate.setImageResource(R.drawable.ic_rate_off)
            } else {
                favorite = true
                movie.favorite = true
                presenter.saveFavoriteMovie(movie)
                movieDetailsRate.setImageResource(R.drawable.ic_rate_on)
            }
            notifyFavoriteStatusChanges(movie.id)
        }
    }

    override fun showMovieDetail(movie: Movie) {
        progress.visibility = View.GONE
        errorContainer.visibility = View.GONE
        detailsContainer.visibility = View.VISIBLE

        movie.favorite = favorite
        movieDetailsTitle.text = movie.title
        movieDetailsRating.rating = movie.rating / 2
        movieDetailsGenres.text = presenter.getFormattedGenres(movie.genres!!)
        if (movie.overview!!.isNotEmpty()) {
            movieDetailsOverview.text = movie.overview
        }

        GlideManager.loadImage(this, movieDetailsBackdrop, movie.backdrop!!)

        setRateClickListener(movie)
        collapsingToolbar.title = movie.title
        collapsingToolbar.setExpandedTitleColor(resources.getColor(android.R.color.transparent))
    }

    override fun showTrailers(trailers: List<Trailer>) {
        movieTrailersLabel.visibility = View.VISIBLE
        trailersLine.visibility = View.VISIBLE

        movieTrailers.removeAllViews()

        for (trailer in trailers) {
            val trailerLayout = layoutInflater.inflate(R.layout.thumbnail_trailer, movieTrailers, false)

            val thumbnail = trailerLayout.findViewById(R.id.thumbnail) as ImageView
            thumbnail.requestLayout()
            thumbnail.setOnClickListener {
                showTrailer(String.format(Constants.YOUTUBE_VIDEO_URL, trailer.key))
            }

            Glide.with(this)
                .load(String.format(Constants.YOUTUBE_THUMBNAIL_URL, trailer.key))
                .apply(RequestOptions.placeholderOf(R.color.colorAccent).centerCrop())
                .into(thumbnail)

            movieTrailers.addView(trailerLayout)
        }
    }

    private fun showTrailer(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun notifyFavoriteStatusChanges(id: Int) {
        val intent = Intent(MoviesFragment.UPDATE_FAVORITE_MESSAGE)
        intent.putExtra(MoviesFragment.MOVIE_ID, id)
        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
    }

    override fun showError() {
        progress.visibility = View.GONE
        errorContainer.visibility = View.VISIBLE
        detailsContainer.visibility = View.GONE
    }

    companion object {
        const val MOVIE_ID = "movie_id"
        const val MOVIE_TITLE = "movie_title"
        const val MOVIE_FAVORITE = "movie_favorite"
    }
}

package org.challenge.btg.ui.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.item_movie.text_title
import org.challenge.btg.R
import org.challenge.btg.data.modelView.MovieModelView
import org.challenge.btg.utils.NetworkCachedTask

class DetailActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val param = intent.getStringExtra(R.string.PARAM_DETAIL.toString()) as String
        val movie = MovieModelView.getMovie(param)

        setContentView(R.layout.activity_detail)

        if (movie != null) {
            text_title.text = movie.title
            text_detail.text = movie.overview
            text_note.text = movie.note
            text_genre.text = "[${movie.genre}]"
            button_favorite_detail.setChecked(movie.favorite)
            NetworkCachedTask(image_poster).execute(movie.poster, movie.id)

            button_favorite_detail.setOnCheckedChangeListener { buttonView, isChecked ->
                MovieModelView.toggleFavorite(movie)
            }
        }
    }
}

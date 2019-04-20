package com.example.leopaim.themoviedb.activity

import android.arch.persistence.room.Room
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.leopaim.themoviedb.BuildConfig.URL_POSTER
import com.squareup.picasso.Picasso
import com.example.leopaim.themoviedb.R
import com.example.leopaim.themoviedb.database.AppDatabase
import com.example.leopaim.themoviedb.database.GenreDao
import com.example.leopaim.themoviedb.database.MovieDao
import com.example.leopaim.themoviedb.model.Movie
import kotlinx.android.synthetic.main.activity_details.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class DetailActivity : AppCompatActivity() {

    private lateinit var movie : Movie
    private lateinit var movieDao: MovieDao
    private lateinit var genreDao: GenreDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val database = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            resources.getString(R.string.db_name)).allowMainThreadQueries()
            .build()

        movieDao = database.movieDao()
        genreDao = database.genreDao()

        val i = intent
        movie = i.getParcelableExtra("MOVIE")

        titleTextView.text = movie.getSafeTitle(this)
        yearTextView.text = "(" + movie.getYear() + ")"
        scoreTextView.text =  movie.getSafeScore(this)
        overviewTextView.text = movie.getSafeOverview(this)
        Picasso.get().load(URL_POSTER + movie.poster).into(posterImageView)
        if(movie.poster == null){
            posterImageView.setImageResource(R.drawable.not_found)
        }

        if(movieDao.hasFavorite(movie.id))
            favoriteImageView.setImageResource(R.drawable.favorite);
        else
            favoriteImageView.setImageResource(R.drawable.unfavorite);


        favoriteImageView.onClick {
            if(movieDao.hasFavorite(movie.id)){
                movieDao.delete(movie)
                favoriteImageView.setImageResource(R.drawable.unfavorite);
            }else{
                movieDao.add(movie)
                favoriteImageView.setImageResource(R.drawable.favorite);
            }
        }

        movie.genre_ids?.forEach {
            genreTextView.text =  genreDao.getGenre(it) + ", " + genreTextView.text.toString()
        }

        if(genreTextView.text.toString().equals("") || genreTextView.text == null)
            genreTextView.text = resources.getString(R.string.no_genre)
        else
            genreTextView.text = genreTextView.text.toString().dropLast(2)



    }
}

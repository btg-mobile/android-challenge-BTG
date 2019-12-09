package com.brunoalmeida.movies.data.dao

import androidx.room.*
import com.brunoalmeida.movies.data.model.Movie
import java.util.*

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie") fun getMovies(): List<Movie>

    @Query("SELECT * FROM movie WHERE title LIKE '%' || :query || '%' OR release_date LIKE '%' || :query || '%'") fun filterMoviesByTitleOrReleaseDate(query: String): List<Movie>

    @Insert(onConflict =  OnConflictStrategy.REPLACE) fun insertMovies(vararg movies: Movie)

    @Update fun updateMovie(movie: Movie)

    @Query("SELECT * FROM movie WHERE uuid = :id ") fun findMovieById(id: Int): Optional<Movie>

    @Delete fun deleteMovie(movie: Movie)
}
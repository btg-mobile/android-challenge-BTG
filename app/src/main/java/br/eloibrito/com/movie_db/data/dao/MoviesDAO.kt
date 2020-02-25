package br.eloibrito.com.movie_db.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.eloibrito.com.movie_db.models.Movies
import io.reactivex.Flowable

@Dao
interface MoviesDAO {

    @Query("SELECT * FROM TB_Movies WHERE id = :id")
    fun getMovies(id: Long) : List<Movies>

    @Query("SELECT * FROM TB_Movies")
    fun getAllMovies() : Flowable<List<Movies>>

    @Insert
    fun insertMovies(movies: Movies)

    @Query("DELETE FROM TB_Movies WHERE id = :id")
    fun deletaMovies(id: Long)
}
package com.example.desafiobtg.db.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.example.desafiobtg.db.entities.Movie
import com.example.desafiobtg.utils.Constants

@Dao
interface MovieDao : BaseDao<Movie> {

    @Query("SELECT * FROM movie ORDER BY popularity DESC LIMIT :itemsPerPage OFFSET ((:page-1)*:itemsPerPage)")
    fun getMoviesForPage(page:Int, itemsPerPage: Int = Constants.ITEMS_IN_PAGE): List<Movie>?

    @Query("DELETE FROM movie WHERE id NOT IN (:movieIdList)")
    fun deleteAllExcept(movieIdList: List<String>)

    @Query("SELECT * FROM movie WHERE id = :movieId")
    fun getMovieById(movieId: String?): @ParameterName(name = "result") Movie?
}
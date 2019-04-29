package com.example.desafiobtg.db.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.example.desafiobtg.db.entities.Genre

@Dao
interface GenreDao: BaseDao<Genre> {

    @Query("SELECT name FROM genre WHERE id IN (:idList)")
    fun getGenreForIds(idList: List<Int>): List<String>

}
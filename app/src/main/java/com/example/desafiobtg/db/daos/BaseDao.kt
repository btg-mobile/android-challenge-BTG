package com.example.desafiobtg.db.daos

import android.arch.persistence.room.*

@Dao
interface BaseDao<in T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(type: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    @Transaction
    fun insertAll(objects: List<T>)

    @Delete
    fun delete(type: T)

    @Update
    fun update(type: T)
}
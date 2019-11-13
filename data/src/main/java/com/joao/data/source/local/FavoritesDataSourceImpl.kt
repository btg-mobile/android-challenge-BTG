package com.joao.data.source.local

import android.content.Context
import com.joao.data.util.Shared
import com.joao.data.util.retrieve
import com.joao.data.util.store
import com.joao.domain.entity.MoviesEntity
import com.joao.domain.entity.MoviesItem

class FavoritesDataSourceImpl(val context: Context) : FavoritesDataSource {
    override suspend fun getFavorites(): MoviesEntity {
        return context.retrieve<MoviesEntity>(Shared.FAVORITES) ?: MoviesEntity(
            0, 0, arrayListOf(), 0
        )
    }

    override suspend fun saveFavorite(item: MoviesItem): Boolean {
        return try {
            val moviesEntity = context.retrieve<MoviesEntity>(Shared.FAVORITES) ?: MoviesEntity(
                0,
                0,
                ArrayList(),
                0
            )
            if (moviesEntity.movies.find { it?.id == item.id } == null) {
                moviesEntity.movies.add(item)
                moviesEntity.totalResults++
            } else {
                moviesEntity.movies.remove(moviesEntity.movies.find { it?.id == item.id })
                moviesEntity.totalResults--
            }
            context.store(Shared.FAVORITES, moviesEntity)
            true
        } catch (ex: Exception) {
            false
        }
    }

}
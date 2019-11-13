package com.joao.data.source.local

import com.joao.domain.entity.MoviesEntity
import com.joao.domain.entity.MoviesItem

interface FavoritesDataSource {
    suspend fun getFavorites():MoviesEntity
    suspend fun saveFavorite(item: MoviesItem):Boolean
}
package com.joao.data.mapper

import com.joao.data.model.MoviesResponse
import com.joao.domain.entity.MoviesEntity
import com.joao.domain.entity.MoviesItem

class MoviesResponseToMoviesMapper : Mapper<MoviesResponse, MoviesEntity>() {
    override fun transform(item: MoviesResponse?) =
        MoviesEntity(
            page = item?.page ?: 0,
            totalPages = item?.totalPages ?: 0,
            totalResults = item?.totalResults ?: 0,
            movies = item?.results?.map { MoviesItemResponseToMoviesItemMapper().transform(it) } as? ArrayList<MoviesItem?>
                ?: arrayListOf())


}
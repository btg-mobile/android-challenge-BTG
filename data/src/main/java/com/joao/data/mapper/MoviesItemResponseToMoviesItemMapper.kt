package com.joao.data.mapper

import com.joao.data.model.MoviesItemResponse
import com.joao.domain.entity.MoviesItem

class MoviesItemResponseToMoviesItemMapper : Mapper<MoviesItemResponse, MoviesItem>() {
    override fun transform(item: MoviesItemResponse?) =
        MoviesItem(
            overview = item?.overview,
            backdropPath = item?.backdropPath,
            genreIds = item?.genreIds,
            id = item?.id,
            originalLanguage = item?.originalLanguage,
            originalTitle = item?.originalTitle,
            popularity = item?.popularity,
            posterPath = item?.posterPath,
            releaseDate = item?.releaseDate,
            title = item?.title,
            video = item?.video,
            voteAverage = item?.voteAverage
        )

}
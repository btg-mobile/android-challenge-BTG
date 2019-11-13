package com.joao.data.mapper

import com.joao.data.model.GenresResponse
import com.joao.domain.entity.Genre

class GenresResponseToListGenres : Mapper<GenresResponse, ArrayList<Genre>>() {
    override fun transform(item: GenresResponse?): ArrayList<Genre> {
        val array = ArrayList<Genre>()
        item?.genres?.forEach {
            array.add(Genre(it?.name.orEmpty(), it?.id ?: 0))
        }
        return array
    }
}
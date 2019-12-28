package org.challenge.btg.data.model

data class Movie (
    val id: String,
    val title: String,
    val poster: String,
    val year: String,
    var favorite: Boolean,
    val overview: String,
    val note: String,
    val genre: String
)

class MovieBuilder {
    var id: String = ""
    var title: String = "Sem título"
    var poster: String = ""
    var year: String = ""
    var favorite: Boolean = false
    var overview: String = "Sem detalhe"
    var note: String = "Sem nota"
    var genre: String = ""

    fun build(): Movie = Movie(id, title, poster,  year, favorite, overview, note, genre)
}

fun movie(block: MovieBuilder.() -> Unit): Movie = MovieBuilder().apply(block).build()
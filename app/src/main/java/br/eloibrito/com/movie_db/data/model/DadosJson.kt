package br.eloibrito.com.movie_db.models

import com.google.gson.annotations.SerializedName

class DadosJson {
    @SerializedName("page")
    var page : Int ? = null
    @SerializedName("total_pages")
    var total_pages : Int ? = null

    @SerializedName("genres")
    var lista_generos : List<Generos>? = null
    @SerializedName("results")
    var lista_movies : List<Movies>? = null

}


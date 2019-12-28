package org.challenge.btg.data.modelView

import android.annotation.SuppressLint
import org.challenge.btg.data.ApiService
import org.challenge.btg.data.model.Movie
import org.challenge.btg.data.model.movie
import org.challenge.btg.utils.StorageHelper
import org.json.JSONObject

object MovieModelView {

    private var listMovies: ArrayList<Movie> = ArrayList()
    private var listFavorite: ArrayList<Movie> = ArrayList()
    private var pageMovies = 1

    fun getMovie(movieId: String): Movie? {
        val jsonResponse = ApiService.getApiMovie(movieId)
        return if (jsonResponse.isNotEmpty()) {
            val jsonObject = JSONObject(jsonResponse)
            val jsonArray = jsonObject.getJSONArray("genres")
            var genres = ""
            for (i in 0 until jsonArray.length()) {
                val item = jsonArray.getJSONObject(i)
                genres += "${item["name"]}, "
            }
            genres = genres.removeSuffix(", ")
            movie {
                id = movieId
                title = jsonObject["title"].toString()
                overview = jsonObject["overview"].toString()
                favorite = (listFavorite.find { it.id == movieId }) is Movie
                note = jsonObject["vote_average"].toString()
                poster = jsonObject["poster_path"].toString()
                genre = genres
            }
        } else null
    }

    fun getPage() = pageMovies

    fun getPopular(loadMore: Boolean=false): ArrayList<Movie> {
        if (listFavorite.isEmpty())
            getFavorite()

        if (loadMore) pageMovies++

        if (pageMovies == 1) listMovies.clear()

        val jsonResponse = ApiService.getApiPopular(pageMovies)
        if (jsonResponse.isNotEmpty()) {
            val jsonObject = JSONObject(jsonResponse)
            val jsonArray = jsonObject.getJSONArray("results")
            for (i in 0 until jsonArray.length()) {
                val item = jsonArray.getJSONObject(i)
                listMovies.add(movie{
                    id = item["id"].toString()
                    title = item["title"].toString()
                    poster = item["poster_path"].toString()
                    year = item["release_date"].toString().substring(0,4)
                    favorite = (listFavorite.find {it.id == id}) is Movie
                    overview = item["overview"].toString()
                    note = item["vote_average"].toString()
                })
            }
        }
        return listMovies
    }

    fun getFavorite(): ArrayList<Movie> {
        listFavorite.clear()
        val jsonResponse = ApiService.getApiFavorites()
        if (jsonResponse.isNotEmpty()) {
            val jsonObject = JSONObject(jsonResponse)
            val jsonArray = jsonObject.getJSONArray("results")
            for (i in 0 until jsonArray.length()) {
                val item = jsonArray.getJSONObject(i)
                listFavorite.add(movie {
                    id = item["id"].toString()
                    title = item["title"].toString()
                    poster = item["poster_path"].toString()
                    year = item["release_date"].toString().substring(0, 4)
                    favorite = true
                    overview = item["overview"].toString()
                    note = item["vote_average"].toString()
                })
            }
        }
        return listFavorite
    }

    @SuppressLint("NewApi")
    fun toggleFavorite(movieToggle: Movie) {
        if (!listFavorite.removeIf {movieFavorite-> movieFavorite.id == movieToggle.id}) {
            listFavorite.add(movieToggle)
        }
        val movieFind = listMovies.find {it.id == movieToggle.id}
        if (movieFind is Movie) movieFind.favorite = !movieFind.favorite
        saveListFavorite()
    }

    private fun saveListFavorite() {
        var moviesIds = ""
        for (movie in listFavorite) moviesIds += "${movie.id},"
        moviesIds = moviesIds.removeSuffix(",")
        StorageHelper.saveFavorites(moviesIds)
    }

    /*
    fun fakeMovies() = listOf(
        movie {
            id = "461130"
            title = "A volta do que não foram"
            poster = ""
            year = "1946"
            favorite = false
        },
        movie {
            id = "4384"
            title = "Poeira alto mar"
            poster = ""
            year = "1964"
            favorite = true
        },
        movie {
            id = "431693"
            title = "As longas tranças de um careca"
            poster = ""
            year = "1980"
            favorite = false
        },
        movie {
            id = "496243"
            title = "A volta do que não foram - O Retorno"
            poster = ""
            year = "2019"
            favorite = true
        },
        movie {
            id = "159323"
            title = "Poeira alto mar II"
            poster = ""
            year = "2018"
            favorite = false
        },
        movie {
            id = "420818"
            title = "As longas tranças de um careca - Episódio 2"
            poster = ""
            year = "2018"
            favorite = false
        }
    )
    */
}
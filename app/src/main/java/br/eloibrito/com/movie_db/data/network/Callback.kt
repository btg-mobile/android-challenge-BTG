package br.eloibrito.com.movie_db.data.network

import android.view.View
import br.eloibrito.com.movie_db.models.Movies

interface Callback {

    fun onRetornoMovie(page : Int?)
}
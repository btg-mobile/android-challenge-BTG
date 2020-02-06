package br.com.themoviebtg.favorites.interaction

import br.com.themoviebtg.BuildConfig
import br.com.themoviebtg.authentication.TokenModel
import br.com.themoviebtg.endpoint.ApiClient
import br.com.themoviebtg.movies.model.MovieModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoritesInteractor {
    interface FavoritesFetchListener {
        fun onFavoritesFetchSuccess(favoritesModelList: List<MovieModel>)
        fun onFavoritesFetchFail(exception: Exception)
    }

    fun fetchFavorites(listener: FavoritesFetchListener) {
        val apiKey = BuildConfig.API_KEY


        generateNewToken(apiKey)


        val favorites = mutableListOf<MovieModel>()

        listener.onFavoritesFetchSuccess(favorites)
    }

    private fun generateNewToken(apiKey: String) {
        val getNewTokenCall = ApiClient.instance.getNewToken(apiKey)

        getNewTokenCall.enqueue(object : Callback<TokenModel> {
            override fun onFailure(call: Call<TokenModel>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(
                call: Call<TokenModel>,
                response: Response<TokenModel>
            ) {
                response.body()?.request_token?.let {
                    allowToken(it, apiKey)
                    println(it)
                }
            }

        })
    }

    private fun allowToken(token: String, apiKey: String) {
        val allowTokenCall = ApiClient.instance.allowToken(token, apiKey)
        allowTokenCall.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                println(response)
            }

        })


    }

}
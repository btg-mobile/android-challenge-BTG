package org.challenge.btg.data

import android.os.AsyncTask
import org.challenge.btg.utils.Global
import org.challenge.btg.utils.StorageHelper
import java.net.URL
import java.net.URLEncoder

object ApiService {

    private const val apiKey = Global.movieDBApiKey
    private const val language = Global.movieDBLanguage
    private const val baseUrl = Global.movieDBBaseURL

    fun getApiPopular(page: Int): String {
        val pageIndex = page.toString()

        var url = baseUrl+"popular"
        url += "?" + URLEncoder.encode("api_key", "UTF-8") + "=" +
                URLEncoder.encode(apiKey,"UTF-8")
        url += "&" + URLEncoder.encode("language", "UTF-8") + "=" +
                URLEncoder.encode(language, "UTF-8")
        url += "&" + URLEncoder.encode("page", "UTF-8") + "=" +
                URLEncoder.encode(pageIndex,"UTF-8")
        return Retriever().execute(url).get()
    }

    fun getApiMovie(id: String): String {
        var url = baseUrl+id
        url += "?" + URLEncoder.encode("api_key", "UTF-8") + "=" +
                URLEncoder.encode(apiKey,"UTF-8")
        url += "&" + URLEncoder.encode("language", "UTF-8") + "=" +
                URLEncoder.encode(language, "UTF-8")
        return Retriever().execute(url).get()
    }

    fun getApiFavorites(): String {
        var json = ""
        var storageIds = StorageHelper.loadFavorites()
        var favoritesIds: List<String> = storageIds.split(",").map { it.trim() }
        favoritesIds.forEach {
            val movieRequest = getApiMovie(it)
            if (movieRequest.isNotEmpty())
                json += "$movieRequest,"
        }
        if (json.isNotEmpty()) {
            json = json.removeSuffix(",")
            json = "{results: [$json]}"
        }
        return json
    }

}

class Retriever : AsyncTask<String, String, String>() {
    override fun doInBackground(vararg args : String?): String {
        val request = args[0].toString()
        return try {
            URL(request).readText()
        } catch (e : Exception) {
            println("EXCEPTION in Retrieve.")
            ""
        }
    }

}
package com.example.leopaim.themoviedb.network

import java.net.URL

class ApiRepository {
    fun doRequest(url: String): String{
        return URL(url).readText()
    }
}
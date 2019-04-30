package com.example.desafiobtg.utils

object Constants {

    // MovieDB Api informs that the Popular list is updated daily, so...
    // We wait one day to update it again
    const val TIME_TO_RELOAD_LIST = 24 * 60 * 60 * 1000 // 1 day
    // Number of items in every page of popular movies
    const val ITEMS_IN_PAGE = 20

    const val POSTER_LIST_IMAGE_URL_PREFIX = "https://image.tmdb.org/t/p/w185"
}
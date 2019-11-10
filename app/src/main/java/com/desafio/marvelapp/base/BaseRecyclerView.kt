package com.desafio.marvelapp.base

import android.view.View

interface BaseRecyclerView {
    fun toggleFavorite(position: Int, view: View)
    fun toggleImage(checked: Boolean): Int
    fun filter(text: String?)
    fun clear()
    fun isListForLetter(): Boolean
}
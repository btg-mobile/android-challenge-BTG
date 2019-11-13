package com.joao.data.util

import android.content.Context
import com.google.gson.Gson

const val pathFile = "PREF"

fun <T> Context.store(shared: Shared, item: T) {
    val settings = this.getSharedPreferences(pathFile, Context.MODE_PRIVATE)
    val editor = settings.edit()
    editor.putString(shared.key, Gson().toJson(item)).apply()
}

inline fun <reified T> Context.retrieve(shared: Shared): T? {
    val settings = this.getSharedPreferences(pathFile, Context.MODE_PRIVATE)
    if (settings.contains(shared.key)) {
        val json = settings.getString(shared.key, "")
        if (json.isNullOrEmpty())
            return null
        return Gson().fromJson(json, T::class.java)
    } else {
        return null
    }
}

fun Context.has(shared: Shared): Boolean {
    val settings = this.getSharedPreferences(pathFile, Context.MODE_PRIVATE)
    return settings.contains(shared.key)
}

fun Context.remove(shared: Shared) {
    val settings = this.getSharedPreferences(pathFile, Context.MODE_PRIVATE)
    if (settings.contains(shared.key)) {
        settings.edit().remove(shared.key).apply()
    }
}

fun Context.clear() {
    val settings = this.getSharedPreferences(pathFile, Context.MODE_PRIVATE)
    settings.edit().clear().apply()
}
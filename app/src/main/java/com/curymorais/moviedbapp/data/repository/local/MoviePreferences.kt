package com.curymorais.moviedbapp.data.repository.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MoviePreferences(context: Context) {
    val PREFS_FILENAME = "com.curymorais.moviedbapp.moviePreferences"
    val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

    fun saveSet(KEY_NAME: String, set:HashSet<String>){
        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.putStringSet(KEY_NAME, set)

        editor!!.commit()
    }

    fun save(KEY_NAME: String, text: String) {

        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.putString(KEY_NAME, text)

        editor!!.commit()
    }

    fun save(KEY_NAME: String, value: Int) {
        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.putInt(KEY_NAME, value)

        editor.commit()
    }

    fun save(KEY_NAME: String, status: Boolean) {

        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.putBoolean(KEY_NAME, status!!)

        editor.commit()
    }

    fun getValueString(KEY_NAME: String): String? {

        return sharedPref.getString(KEY_NAME, null)


    }

    fun getStringSet(KEY_NAME: String): Set<String>? {

        return sharedPref.getStringSet(KEY_NAME, null)


    }

    fun getValueInt(KEY_NAME: String): Int {

        return sharedPref.getInt(KEY_NAME, 0)
    }

    fun getValueBoolien(KEY_NAME: String, defaultValue: Boolean): Boolean {

        return sharedPref.getBoolean(KEY_NAME, defaultValue)

    }

    fun clearSharedPreference() {

        val editor: SharedPreferences.Editor = sharedPref.edit()

        //sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        editor.clear()
        editor.commit()
    }

    fun removeValue(KEY_NAME: String) {

        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.remove(KEY_NAME)
        editor.commit()
    }

    fun jsonToHash(json : String): HashMap<Int,String>{
        val gson = Gson().fromJson<HashMap<Int,String>>(json)
        return gson
    }

    fun hashToJson(hash : HashMap<Int,String>): String {
        val gson = Gson()
        return gson.toJson(hash).toString()
    }
}

inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object: TypeToken<T>() {}.type)

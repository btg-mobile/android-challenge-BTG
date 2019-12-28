package org.challenge.btg.utils

import java.io.File
import java.io.IOException

object StorageHelper {

    private const val fileName: String = ""

    fun saveFavorites(ids: String) {
        try {
            val fileFavorite = File(fileName)
            fileFavorite.printWriter().use { out -> out.println(ids) }
        } catch (e: IOException) {
            println("ERROR I/O - Save Favorites")
        }
    }

    fun loadFavorites(): String {
        return try {
            val lines: List<String> = File(fileName).readLines()
            lines[0]
        } catch (e: IOException) {
            println("ERROR I/O - Load Favorites")
            ""
        }
    }

}

package com.uchoa.btg.movie.dao.sqlite

import android.content.ContentValues
import android.content.Context
import com.uchoa.btg.movie.models.Movie

class FavoriteDao(context: Context) {

    private var dbHelper: MovieDbHelper = MovieDbHelper(context)

    fun insert(movie: Movie): Long {

        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(MovieDBContract.FavoriteEntry.COLUMN_NAME_MOVIE_ID, movie.id)
            put(MovieDBContract.FavoriteEntry.COLUMN_NAME_MOVIE_TITLE, movie.title)
            put(MovieDBContract.FavoriteEntry.COLUMN_NAME_RELEASE_DATE, movie.releaseDate)
            put(MovieDBContract.FavoriteEntry.COLUMN_NAME_POSTER_PATH, movie.posterPath)
        }

        return db?.insert(MovieDBContract.FavoriteEntry.TABLE_NAME, null, values)!!
    }

    fun get(movieId: Int): MutableList<Movie> {
        val selection = "${MovieDBContract.FavoriteEntry.COLUMN_NAME_MOVIE_ID} = ?"
        val selectionArgs = arrayOf("$movieId")

        return executeQuery(selection, selectionArgs)
    }

    fun getAll(): MutableList<Movie> {
        return executeQuery(null, null)
    }

    private fun executeQuery(selection: String?, args: Array<String>?): MutableList<Movie> {

        val tableName = MovieDBContract.FavoriteEntry.TABLE_NAME

        val projection = arrayOf(
            MovieDBContract.FavoriteEntry.COLUMN_NAME_MOVIE_ID,
            MovieDBContract.FavoriteEntry.COLUMN_NAME_MOVIE_TITLE,
            MovieDBContract.FavoriteEntry.COLUMN_NAME_RELEASE_DATE,
            MovieDBContract.FavoriteEntry.COLUMN_NAME_POSTER_PATH
        )

        val sortOrder = "${MovieDBContract.FavoriteEntry.COLUMN_NAME_MOVIE_TITLE} ASC"

        val data = mutableListOf<Movie>()
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            tableName,
            projection,
            selection,
            args,
            null,
            null,
            sortOrder
        )

        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(MovieDBContract.FavoriteEntry.COLUMN_NAME_MOVIE_ID))
                val title = getString(getColumnIndexOrThrow(MovieDBContract.FavoriteEntry.COLUMN_NAME_MOVIE_TITLE))
                val releaseDate =
                    getString(getColumnIndexOrThrow(MovieDBContract.FavoriteEntry.COLUMN_NAME_RELEASE_DATE))
                val imagePath = getString(getColumnIndexOrThrow(MovieDBContract.FavoriteEntry.COLUMN_NAME_POSTER_PATH))

                val movie = com.uchoa.btg.movie.models.Movie()
                movie.id = id
                movie.title = title
                movie.releaseDate = releaseDate
                movie.posterPath = imagePath
                movie.favorite = true

                data.add(movie)
            }
        }

        return data
    }

    fun delete(movieId: Int): Boolean {

        val db = dbHelper.readableDatabase

        val selection = "${MovieDBContract.FavoriteEntry.COLUMN_NAME_MOVIE_ID} = ?"
        val selectionArgs = arrayOf("$movieId")
        val deletedRows = db.delete(MovieDBContract.FavoriteEntry.TABLE_NAME, selection, selectionArgs)

        return deletedRows > 0
    }

    fun onDestroy() {
        dbHelper.close()
    }
}
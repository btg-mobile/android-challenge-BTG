package com.uchoa.btg.movie.dao.sqlite

object MovieDBContract {

    object FavoriteEntry {
        const val TABLE_NAME = "favorite"
        const val COLUMN_NAME_ID = "_id"
        const val COLUMN_NAME_MOVIE_ID = "movie_id"
        const val COLUMN_NAME_MOVIE_TITLE = "title"
        const val COLUMN_NAME_RELEASE_DATE = "release_date"
        const val COLUMN_NAME_POSTER_PATH = "image_path"
    }

    const val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${FavoriteEntry.TABLE_NAME} (" +
                "${FavoriteEntry.COLUMN_NAME_ID} INTEGER PRIMARY KEY," +
                "${FavoriteEntry.COLUMN_NAME_MOVIE_ID} INTEGER," +
                "${FavoriteEntry.COLUMN_NAME_MOVIE_TITLE} TEXT," +
                "${FavoriteEntry.COLUMN_NAME_RELEASE_DATE} TEXT," +
                "${FavoriteEntry.COLUMN_NAME_POSTER_PATH} TEXT)"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${FavoriteEntry.TABLE_NAME}"
}
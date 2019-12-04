package com.example.android_challenge_btg.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.android_challenge_btg.models.Genre;
import com.example.android_challenge_btg.models.Movie;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DataBaseService extends OrmLiteSqliteOpenHelper {

    private static DataBaseService instance;

    private static final String DATABASE_NAME = "db_challenge_btg";
    private static final int DATABASE_VERSION = 1;

    public DataBaseService(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DataBaseService getInstance(Context context) {
        if(instance == null) {
            instance = OpenHelperManager.getHelper(context, DataBaseService.class);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, Genre.class);
            TableUtils.createTableIfNotExists(connectionSource, Movie.class);
        } catch (SQLException e) {
            //Todo: treatException
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}

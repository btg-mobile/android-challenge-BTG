package com.dacruzl2.btgdesafio.model.ViewModel;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.dacruzl2.btgdesafio.model.pojos.pojoteste.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class FavoritosDatabase extends RoomDatabase {

    private static FavoritosDatabase favoritosDatabase;

    public abstract FavoritosDao getFavoritosDao();

    public static FavoritosDatabase getInstance(Context context) {
        if(favoritosDatabase == null)
            favoritosDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    FavoritosDatabase.class, "favoritos_database")
                    .fallbackToDestructiveMigration().build();
        return favoritosDatabase;
    }
}

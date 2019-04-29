package com.dacruzl2.btgdesafio.model.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.dacruzl2.btgdesafio.model.pojos.pojoteste.Movie;

import java.util.List;

@Dao
public interface FavoritosDao {

    @Query("DELETE FROM Movie")
    void deleteAll();

    @Query("SELECT * FROM Movie")
    LiveData<List<Movie>> loadAllMovies();

    @Query("SELECT * FROM Movie WHERE mId = :id")
    LiveData<Movie> loadMovieById(int id);

    @Query("SELECT * FROM Movie WHERE favorite = 1 AND mid = :id")
    LiveData<Movie> getFavourite(int id);

    @Query("SELECT * FROM Movie WHERE title = :movieTitle")
    Movie loadMovie(String movieTitle);

    @Insert
    void saveMovieToFavorites(Movie movie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(Movie movie);

    @Delete
    void removeMovieFromFavorites(Movie movie);
}

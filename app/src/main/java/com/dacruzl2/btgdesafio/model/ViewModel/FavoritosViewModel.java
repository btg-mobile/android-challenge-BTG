package com.dacruzl2.btgdesafio.model.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.dacruzl2.btgdesafio.model.pojos.pojoteste.Movie;

import java.util.List;

public class FavoritosViewModel extends AndroidViewModel {

    public  FavoritosRepository repository;
    private LiveData<List<Movie>> allmovies;
    private LiveData<Movie> movie;
    private LiveData<Movie> movieLiveData;


    public FavoritosViewModel(@NonNull Application application) {
        super(application);
        FavoritosDatabase db = FavoritosDatabase.getInstance(application);
        allmovies = db.getFavoritosDao().loadAllMovies();
       // movieLiveData = db.getFavoritosDao().loadMovie(title);

    }

    //public LiveData<List<Movieold>> getMovies() {
    //    return allmovies;
  //  }
    public LiveData<Movie> getMovieLiveDataTitle() {
        return movieLiveData;
    }

    public LiveData<List<Movie>> getaAllMovies() {
        return allmovies;
    }

  /*  public void loadMovieTitle(String title)
    {
        repository.loadMovieTitle(title);
    }*/

    public void discoverFavorite(int loadFavoriteState){
        repository.loadFavoriteState(loadFavoriteState);
    }

    public void insert(Movie movie) {
        repository.insert(movie);
    }
    public void update(Movie movie){
        repository.update(movie);
    }
    public void delete(Movie movie){
        repository.delete(movie);
    }

    //public LiveData<List<LocationItems>> getlastLocation() {
      //  return oneLocation;
   // }

    public void init(int mMovieId, int page, final IMovieDetailsCallback IMovieDetailsCallback) {
        movie = repository.getMovie(mMovieId, IMovieDetailsCallback);

    }
    public LiveData<Movie> getMovie() {
        return movie;
    }
}

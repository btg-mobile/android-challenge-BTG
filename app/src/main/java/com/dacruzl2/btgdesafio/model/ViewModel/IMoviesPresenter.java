package com.dacruzl2.btgdesafio.model.ViewModel;

import androidx.lifecycle.LiveData;

import com.dacruzl2.btgdesafio.model.pojos.pojoteste.Movie;

public interface IMoviesPresenter {

    //Method for getMovies Details
    LiveData<Movie> getMovie(int movieId, final IMovieDetailsCallback iMovieDetailsCallback);

}
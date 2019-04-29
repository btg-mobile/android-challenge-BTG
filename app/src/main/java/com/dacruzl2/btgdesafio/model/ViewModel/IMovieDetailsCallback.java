package com.dacruzl2.btgdesafio.model.ViewModel;

import com.dacruzl2.btgdesafio.model.pojos.pojoteste.Movie;

public interface IMovieDetailsCallback {
    //On Success movie details received
    void onSuccess(Movie movie);

    void onError();

}

package com.dacruzl2.btgdesafio.model.inter;

import android.content.Context;

import com.dacruzl2.btgdesafio.model.pojos.RootGenres;
import com.dacruzl2.btgdesafio.model.pojos.pojoteste.Movie;
import com.dacruzl2.btgdesafio.model.pojos.pojoteste.Root;

import retrofit2.Call;

public interface IRetrofitManager {

    Call<Root> retrievePopularMovies(String apiKey, int page, String language);

    Call<Root> retrieveMoviesBySearch(String apiKey, int page, String query);

    Call<Movie> getMovie(String apiKey, int id, String language);

    Call<RootGenres> getGenres(String apiKey, String language);

    void conn(Context context);
}

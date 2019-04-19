package br.com.ricardo.filmespopulares.data.network.api;

import br.com.ricardo.filmespopulares.data.network.response.ResultFilms;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FilmService {

    public static final String BASE_URL = "https://api.themoviedb.org/3/";

    @GET("movie/popular")
    Call<ResultFilms> getPopularFilms(@Query("api_key") String apiKey);

}

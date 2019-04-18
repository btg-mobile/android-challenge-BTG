package br.com.ricardo.filmespopulares.api;

import br.com.ricardo.filmespopulares.pojo.PopularResultFilms;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FilmService {

    public static final String BASE_URL = "https://api.themoviedb.org/3/";

    @GET("movie/popular")
    Call<PopularResultFilms> getPopularFilms(@Query("api_key") String apiKey);

//    @GET("genre/movie/list")
//    Call<PopularResultFilms> getPopularFilms(@Query("api_key") String apiKey);

}

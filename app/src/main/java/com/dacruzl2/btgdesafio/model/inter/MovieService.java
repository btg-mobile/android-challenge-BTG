package com.dacruzl2.btgdesafio.model.inter;

import com.dacruzl2.btgdesafio.model.pojos.RootGenres;
import com.dacruzl2.btgdesafio.model.pojos.pojoteste.Movie;
import com.dacruzl2.btgdesafio.model.pojos.pojoteste.Root;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {

    @GET("movie/popular")
    Call<Root> getPopularMovies(@Query("api_key") String apiKey,
                                @Query("page") int page,
                                @Query("language") String language);

    @GET("search/movie")
    Call<Root> getMoviesByQuery(@Query("api_key") String apiKey,
                                @Query("page") int page,
                                @Query("query") String query);

    @GET("movie/{movie_id}")
    Call<Movie> getMovie(
            @Path("movie_id") int id,
            @Query("api_key") String apiKey,
            @Query("language") String language);

    @GET("genre/movie/list")
    Call<RootGenres> getGenre(@Query("api_key") String apiKey,
                                @Query("language") String language);

}
package br.com.ricardo.filmespopulares.data.network.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    private static FilmService service;

    public static FilmService getInstance(){

        if(service == null){

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(FilmService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service = retrofit.create(FilmService.class);

        }

        return service;

    }
}


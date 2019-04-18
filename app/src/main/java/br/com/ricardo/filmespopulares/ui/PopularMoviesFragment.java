package br.com.ricardo.filmespopulares.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.ricardo.filmespopulares.R;
import br.com.ricardo.filmespopulares.api.FilmService;
import br.com.ricardo.filmespopulares.pojo.PopularResponseFilm;
import br.com.ricardo.filmespopulares.pojo.PopularResultFilms;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;


public class PopularMoviesFragment extends Fragment{

    private EditText editPopularMovieSearch;
    private RecyclerView recyclerPopularMovie;

    private PopularMovieListAdapter adapter;

    private List<PopularResponseFilm> movieList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View popularView = inflater.inflate(R.layout.fragment_popular_movies, container, false);

        editPopularMovieSearch = (EditText) popularView.findViewById(R.id.edit_popular_ml_search);
        recyclerPopularMovie = (RecyclerView) popularView.findViewById(R.id.recycler_popular_ml);

        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerPopularMovie.setLayoutManager(gridLayoutManager);

        movieList = new ArrayList<>();

        connectService();

        return popularView;
    }


    public void connectService(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(FilmService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FilmService service = retrofit.create(FilmService.class);

        service.getPopularFilms("b70848b875278d63417beecbdddc4841")
                .enqueue(new Callback<PopularResultFilms>() {
                    @Override
                    public void onResponse(Call<PopularResultFilms> call, Response<PopularResultFilms> response) {

                        if(!response.isSuccessful()) {
                            Log.i(TAG, "Erro: " + response.code());
                            showError();

                        } else {
                            PopularResultFilms resultFilms = response.body();

                            if(resultFilms != null){
                                for(PopularResponseFilm rf : resultFilms.getResults()){

                                    movieList.add(rf);

                                }
                            }

                            adapter = new PopularMovieListAdapter(movieList);
                            recyclerPopularMovie.setAdapter(adapter);

                            adapter.setOnItemClickListener(new PopularMovieListAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {

                                    Intent intent = new Intent(getActivity(), MovieDetail.class);

                                    PopularResponseFilm film = movieList.get(position);

                                    intent.putExtra(MovieDetail.EXTRA_FILM, film);
                                    startActivity(intent);


                                }
                            });
                        }

                    }

                    @Override
                    public void onFailure(Call<PopularResultFilms> call, Throwable t) {

                    }
                });

    }

    private void showError() {
        Toast.makeText(getActivity(), "Erro de Conexão", Toast.LENGTH_SHORT).show();
    }
}

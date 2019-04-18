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
import br.com.ricardo.filmespopulares.data.network.api.ApiService;
import br.com.ricardo.filmespopulares.data.network.model.Film;
import br.com.ricardo.filmespopulares.data.network.response.FilmMapper;
import br.com.ricardo.filmespopulares.data.network.response.PopularResponseFilm;
import br.com.ricardo.filmespopulares.data.network.response.PopularResultFilms;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class PopularMoviesFragment extends Fragment{

    private EditText editPopularMovieSearch;
    private RecyclerView recyclerPopularMovie;

    private PopularMovieListAdapter adapter;
    private List<Film> filmList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View popularView = inflater.inflate(R.layout.fragment_popular_movies, container, false);

        editPopularMovieSearch = (EditText) popularView.findViewById(R.id.edit_popular_ml_search);
        recyclerPopularMovie = (RecyclerView) popularView.findViewById(R.id.recycler_popular_ml);

        adapter = new PopularMovieListAdapter();
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerPopularMovie.setLayoutManager(gridLayoutManager);
        recyclerPopularMovie.setAdapter(adapter);

        getMovies();

        return popularView;
    }


    public void getMovies(){
        ApiService.getInstance().getPopularFilms("b70848b875278d63417beecbdddc4841")
                .enqueue(new Callback<PopularResultFilms>() {
                    @Override
                    public void onResponse(Call<PopularResultFilms> call, Response<PopularResultFilms> response) {

                        if(!response.isSuccessful()) {
                            Log.i(TAG, "Erro: " + response.code());
                            showError();

                        } else {
                            filmList = FilmMapper
                                    .setFilmDomain(response.body().getResults());

                            adapter.setFilm(filmList);
                        }

                        adapter.setOnItemClickListener(new PopularMovieListAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {

                                Intent intent = new Intent(getActivity(), MovieDetail.class);

                                Film film = filmList.get(position);

                                intent.putExtra(MovieDetail.EXTRA_FILM, film);
                                startActivity(intent);


                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<PopularResultFilms> call, Throwable t) {
                        showError();
                        Log.i("FUDEEEEUU!!!", t.getMessage());
                    }
            });

    }

    private void showError() {
        Toast.makeText(getActivity(), "Erro de Conexão", Toast.LENGTH_SHORT).show();
    }
}

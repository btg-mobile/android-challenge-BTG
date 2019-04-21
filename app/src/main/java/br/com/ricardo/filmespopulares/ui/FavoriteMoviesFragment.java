package br.com.ricardo.filmespopulares.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.ricardo.filmespopulares.R;
import br.com.ricardo.filmespopulares.data.network.api.ApiService;
import br.com.ricardo.filmespopulares.data.network.model.Film;
import br.com.ricardo.filmespopulares.data.network.response.ResponseFilm;
import br.com.ricardo.filmespopulares.data.network.response.ResultFilms;
import br.com.ricardo.filmespopulares.utils.HideKeyboard;
import br.com.ricardo.filmespopulares.utils.KeepData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class FavoriteMoviesFragment extends Fragment {

    private static final String PREFS_NAME = "Preferences";
    public static String TAG_FAILURE = "Response - Failure";

    private FrameLayout frameFavoriteMovie;
    private ProgressBar progressBarFavoriteMovie;
    private SwipeRefreshLayout swipeFavoriteMovie;
    private EditText editFavoriteMovieSearch;
    private RecyclerView recyclerFavoriteMovie;

    private KeepData prefs;

    private FavoriteMovieListAdapter adapter;
    private Film film;
    private List<Film> filmList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View popularView = inflater.inflate(R.layout.fragment_favorite_movies, container, false);

        prefs = new KeepData(getActivity().getSharedPreferences(PREFS_NAME, MODE_PRIVATE));

        frameFavoriteMovie = (FrameLayout) popularView.findViewById(R.id.frame_favorite_movielist);
        progressBarFavoriteMovie = (ProgressBar) popularView.findViewById(R.id.progressBar_favorite_movielist);
        swipeFavoriteMovie = (SwipeRefreshLayout) popularView.findViewById(R.id.swipe_container_favorite_movie_list);
        editFavoriteMovieSearch = (EditText) popularView.findViewById(R.id.edit_favorite_ml_search);
        recyclerFavoriteMovie = (RecyclerView) popularView.findViewById(R.id.recycler_favorite_ml);

        frameFavoriteMovie.setVisibility(View.VISIBLE);
        progressBarFavoriteMovie.setVisibility(View.VISIBLE);

        adapter = new FavoriteMovieListAdapter();
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerFavoriteMovie.setLayoutManager(linearLayoutManager);
        recyclerFavoriteMovie.setAdapter(adapter);

        getFavoriteMovies();

        editFavoriteMovieSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                searchMovie(editFavoriteMovieSearch.getText().toString().trim());
                HideKeyboard.hide(getActivity(), editFavoriteMovieSearch);

                frameFavoriteMovie.setVisibility(View.VISIBLE);
                progressBarFavoriteMovie.setVisibility(View.VISIBLE);

                return false;
            }
        });

        swipeFavoriteMovie.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getFavoriteMovies();
                swipeFavoriteMovie.setRefreshing(false);

                frameFavoriteMovie.setVisibility(View.VISIBLE);
                progressBarFavoriteMovie.setVisibility(View.VISIBLE);

            }
        });

        return popularView;
    }

    public void searchMovie(final String movie){

        if(TextUtils.isEmpty(movie)){
            getFavoriteMovies();
            showError("Campo vazio. Digite o nome do filme.");
        } else {

            ApiService.getInstance().getPopularFilms("b70848b875278d63417beecbdddc4841")
                    .enqueue(new Callback<ResultFilms>() {
                        @Override
                        public void onResponse(Call<ResultFilms> call, Response<ResultFilms> response) {

                            if(!response.isSuccessful()) {
                                Log.i(TAG_FAILURE, "Erro: " + response.code());
                                showError("Falha na conexão.");

                            } else {

                                frameFavoriteMovie.setVisibility(View.GONE);
                                progressBarFavoriteMovie.setVisibility(View.GONE);

                                ResultFilms resultFilms = response.body();
                                filmList = new ArrayList<>();

                                for(ResponseFilm rf : resultFilms.getResults()){

                                    if(rf.getTitle().contains(movie) || rf.getReleaseDate().contains(movie)){

                                        if(prefs.recoverFlagFavorite(String.valueOf(rf.getId()))){

                                            final Film film = new Film(rf.getId(), rf.getRate(), rf.getTitle(), rf.getPosterPath(),
                                                    rf.getOriginalTitle(), rf.getGenres(), rf.getBackdropPath(),
                                                    rf.getOverview(), rf.getReleaseDate());

                                            filmList.add(film);
                                        }
                                    }
                                }

                                if(filmList.size() == 0){
                                    showError("Não existe nenhum filme com este nome.");
                                    getFavoriteMovies();
                                }

                                adapter.setFavoriteFilm(filmList);
                                editFavoriteMovieSearch.setText("");
                            }

                            adapter.setOnItemClickListener(new FavoriteMovieListAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {

                                    Intent intent = new Intent(getActivity(), MovieDetail.class);

                                    film = filmList.get(position);

                                    intent.putExtra(MovieDetail.EXTRA_FILM, film);
                                    startActivity(intent);
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<ResultFilms> call, Throwable t) {
                            Log.i(TAG_FAILURE, t.getMessage());
                            showError("Falha na conexão.");
                        }
                    });
        }
    }


    public void getFavoriteMovies(){

        ApiService.getInstance().getPopularFilms("b70848b875278d63417beecbdddc4841")
                .enqueue(new Callback<ResultFilms>() {
                    @Override
                    public void onResponse(Call<ResultFilms> call, Response<ResultFilms> response) {

                        if(!response.isSuccessful()) {
                            Log.i(TAG_FAILURE, "Erro: " + response.code());
                            showError("Falha na conexão.");

                        } else {

                            frameFavoriteMovie.setVisibility(View.GONE);
                            progressBarFavoriteMovie.setVisibility(View.GONE);

                            ResultFilms resultFilms = response.body();

                            filmList = new ArrayList<>();

                            for(ResponseFilm rf : resultFilms.getResults()){

                                if(prefs.recoverFlagFavorite(String.valueOf(rf.getId()))){

                                    final Film film = new Film(rf.getId(), rf.getRate(), rf.getTitle(), rf.getPosterPath(),
                                            rf.getOriginalTitle(), rf.getGenres(), rf.getBackdropPath(),
                                            rf.getOverview(), rf.getReleaseDate());

                                    filmList.add(film);
                                }
                            }

                            adapter.setFavoriteFilm(filmList);

                        }

                        adapter.setOnItemClickListener(new FavoriteMovieListAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {

                                Intent intent = new Intent(getActivity(), MovieDetail.class);

                                film = filmList.get(position);

                                intent.putExtra(MovieDetail.EXTRA_FILM, film);
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<ResultFilms> call, Throwable t) {
                        Log.i(TAG_FAILURE, t.getMessage());
                        showError("Falha na conexão.");
                    }
                });
    }

    private void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();

        frameFavoriteMovie.setVisibility(View.VISIBLE);
        progressBarFavoriteMovie.setVisibility(View.VISIBLE);

        getFavoriteMovies();
    }
}
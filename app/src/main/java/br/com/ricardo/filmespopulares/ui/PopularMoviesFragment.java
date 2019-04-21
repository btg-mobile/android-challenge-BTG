package br.com.ricardo.filmespopulares.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
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
import br.com.ricardo.filmespopulares.data.network.response.FilmMapper;
import br.com.ricardo.filmespopulares.data.network.response.ResponseFilm;
import br.com.ricardo.filmespopulares.data.network.response.ResultFilms;
import br.com.ricardo.filmespopulares.utils.HideKeyboard;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PopularMoviesFragment extends Fragment {

    public static String TAG_FAILURE = "Response - Failure";

    private FrameLayout framePopularMovie;
    private ProgressBar progressBarPopularMovie;
    private SwipeRefreshLayout swipePopularMovie;
    private EditText editPopularMovieSearch;
    private RecyclerView recyclerPopularMovie;

    private PopularMovieListAdapter adapter;
    private Film film;
    private List<Film> filmList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View popularView = inflater.inflate(R.layout.fragment_popular_movies, container, false);

        framePopularMovie = (FrameLayout) popularView.findViewById(R.id.frame_popular_movielist);
        progressBarPopularMovie = (ProgressBar) popularView.findViewById(R.id.progressBar_popular_movielist);
        swipePopularMovie = (SwipeRefreshLayout) popularView.findViewById(R.id.swipe_container_popular_movie_list);
        editPopularMovieSearch = (EditText) popularView.findViewById(R.id.edit_popular_ml_search);
        recyclerPopularMovie = (RecyclerView) popularView.findViewById(R.id.recycler_popular_ml);

        framePopularMovie.setVisibility(View.VISIBLE);
        progressBarPopularMovie.setVisibility(View.VISIBLE);

        adapter = new PopularMovieListAdapter();
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerPopularMovie.setLayoutManager(gridLayoutManager);
        recyclerPopularMovie.setAdapter(adapter);

        getMovies();

        editPopularMovieSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                searchMovie(editPopularMovieSearch.getText().toString().trim());
                HideKeyboard.hide(getActivity(), editPopularMovieSearch);

                framePopularMovie.setVisibility(View.VISIBLE);
                progressBarPopularMovie.setVisibility(View.VISIBLE);

                return false;
            }
        });

        swipePopularMovie.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getMovies();
                swipePopularMovie.setRefreshing(false);

                framePopularMovie.setVisibility(View.VISIBLE);
                progressBarPopularMovie.setVisibility(View.VISIBLE);

            }
        });

        return popularView;
    }

    public void searchMovie(final String movie){

        if(TextUtils.isEmpty(movie)){
            getMovies();
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

                                framePopularMovie.setVisibility(View.GONE);
                                progressBarPopularMovie.setVisibility(View.GONE);

                                ResultFilms resultFilms = response.body();
                                filmList = new ArrayList<>();

                                for(ResponseFilm rf : resultFilms.getResults()){

                                    if(rf.getTitle().contains(movie)){

                                        final Film film = new Film(rf.getId(), rf.getRate(), rf.getTitle(), rf.getPosterPath(),
                                                rf.getOriginalTitle(), rf.getGenres(), rf.getBackdropPath(),
                                                rf.getOverview(), rf.getReleaseDate());

                                        filmList.add(film);
                                    }
                                }

                                if(filmList.size() == 0){
                                    showError("Não existe nenhum filme com este nome.");
                                    getMovies();
                                }

                                adapter.setFilm(filmList);
                                editPopularMovieSearch.setText("");
                            }

                            adapter.setOnItemClickListener(new PopularMovieListAdapter.OnItemClickListener() {
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


    public void getMovies(){
        ApiService.getInstance().getPopularFilms("b70848b875278d63417beecbdddc4841")
                .enqueue(new Callback<ResultFilms>() {
                    @Override
                    public void onResponse(Call<ResultFilms> call, Response<ResultFilms> response) {

                        if(!response.isSuccessful()) {
                            Log.i(TAG_FAILURE, "Erro: " + response.code());
                            showError("Falha na conexão.");

                        } else {

                            framePopularMovie.setVisibility(View.GONE);
                            progressBarPopularMovie.setVisibility(View.GONE);

                            filmList = FilmMapper.setFilmDomain(response.body().getResults());

                            adapter.setFilm(filmList);
                        }

                        adapter.setOnItemClickListener(new PopularMovieListAdapter.OnItemClickListener() {
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

        framePopularMovie.setVisibility(View.VISIBLE);
        progressBarPopularMovie.setVisibility(View.VISIBLE);

        getMovies();
    }
}

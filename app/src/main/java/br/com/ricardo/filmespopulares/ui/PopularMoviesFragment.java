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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.ricardo.filmespopulares.R;
import br.com.ricardo.filmespopulares.data.network.api.ApiService;
import br.com.ricardo.filmespopulares.data.network.model.Film;
import br.com.ricardo.filmespopulares.data.network.response.FilmMapper;
import br.com.ricardo.filmespopulares.data.network.response.ResponseFilm;
import br.com.ricardo.filmespopulares.data.network.response.ResultFilms;
import br.com.ricardo.filmespopulares.utils.Connection;
import br.com.ricardo.filmespopulares.utils.ErrorMessage;
import br.com.ricardo.filmespopulares.utils.HideKeyboard;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PopularMoviesFragment extends Fragment {

    public static String TAG_FAILURE = "Response - Failure";

    private FrameLayout framePopularMovie;
    private ProgressBar progressBarPopularMovie;
    private TextView textPopularListEmptyNoNetwork;
    private SwipeRefreshLayout swipePopularMovie;
    private EditText editPopularMovieSearch;
    private RecyclerView recyclerPopularMovie;

    private Connection connection;
    private ErrorMessage errorMessage;
    private PopularMovieListAdapter adapter;
    private Film film;
    private List<Film> filmList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View popularView = inflater.inflate(R.layout.fragment_popular_movies, container, false);

        errorMessage = new ErrorMessage();
        connection = new Connection();

        framePopularMovie = (FrameLayout) popularView.findViewById(R.id.frame_popular_movielist);
        progressBarPopularMovie = (ProgressBar) popularView.findViewById(R.id.progressBar_popular_movielist);
        textPopularListEmptyNoNetwork = (TextView) popularView.findViewById(R.id.text_popular_notice_no_network);
        swipePopularMovie = (SwipeRefreshLayout) popularView.findViewById(R.id.swipe_container_popular_movie_list);
        editPopularMovieSearch = (EditText) popularView.findViewById(R.id.edit_popular_ml_search);
        recyclerPopularMovie = (RecyclerView) popularView.findViewById(R.id.recycler_popular_ml);

        framePopularMovie.setVisibility(View.VISIBLE);
        progressBarPopularMovie.setVisibility(View.VISIBLE);

        adapter = new PopularMovieListAdapter();
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerPopularMovie.setLayoutManager(gridLayoutManager);
        recyclerPopularMovie.setAdapter(adapter);

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

        getMovies();

        return popularView;
    }


    public void searchMovie(final String movie){

        if(TextUtils.isEmpty(movie)){
            getMovies();
            errorMessage.showError(getActivity(), getString(R.string.search_bar_empty));

        } else {

            ApiService.getInstance().getPopularFilms("b70848b875278d63417beecbdddc4841")
                    .enqueue(new Callback<ResultFilms>() {
                        @Override
                        public void onResponse(Call<ResultFilms> call, Response<ResultFilms> response) {

                            if(!response.isSuccessful()) {
                                Log.i(TAG_FAILURE, "Erro: " + response.code());
                                errorMessage.showError(getActivity(), getString(R.string.connection_failure));

                            } else {

                                framePopularMovie.setVisibility(View.GONE);
                                progressBarPopularMovie.setVisibility(View.GONE);

                                ResultFilms resultFilms = response.body();
                                filmList = new ArrayList<>();

                                for(ResponseFilm rf : resultFilms.getResults()){

                                    String aux = rf.getTitle();

                                    if(aux.toLowerCase().contains(movie.toLowerCase())){

                                        final Film film = new Film(rf.getId(), rf.getRate(), rf.getTitle(), rf.getPosterPath(),
                                                rf.getOriginalTitle(), rf.getGenres(), rf.getBackdropPath(),
                                                rf.getOverview(), rf.getReleaseDate());

                                        filmList.add(film);
                                    }
                                }

                                if(filmList.size() == 0){
                                    errorMessage.showError(getActivity(), getString(R.string.movie_list_empty));
                                    getMovies();
                                }

                                adapter.setPopularFilm(filmList);
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
                            errorMessage.showError(getActivity(), getString(R.string.connection_failure));
                        }
                    });
        }
    }


    public void getMovies(){

        if(!connection.verifyConnection(getActivity())){

            framePopularMovie.setVisibility(View.VISIBLE);
            progressBarPopularMovie.setVisibility(View.GONE);
            textPopularListEmptyNoNetwork.setVisibility(View.VISIBLE);

            errorMessage.showError(getActivity(), getString(R.string.connection_network));
        } else {

            ApiService.getInstance().getPopularFilms("b70848b875278d63417beecbdddc4841")
                    .enqueue(new Callback<ResultFilms>() {
                        @Override
                        public void onResponse(Call<ResultFilms> call, Response<ResultFilms> response) {

                            if(!response.isSuccessful()) {
                                Log.i(TAG_FAILURE, "Erro: " + response.code());
                                errorMessage.showError(getActivity(), getString(R.string.connection_failure));

                            } else {

                                framePopularMovie.setVisibility(View.GONE);
                                progressBarPopularMovie.setVisibility(View.GONE);

                                filmList = FilmMapper.setFilmDomain(response.body().getResults());

                                adapter.setPopularFilm(filmList);
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

                            framePopularMovie.setVisibility(View.GONE);
                            progressBarPopularMovie.setVisibility(View.GONE);

                            errorMessage.showError(getActivity(), getString(R.string.connection_failure));
                        }
                    });

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        framePopularMovie.setVisibility(View.VISIBLE);
        progressBarPopularMovie.setVisibility(View.VISIBLE);

        getMovies();
    }
}

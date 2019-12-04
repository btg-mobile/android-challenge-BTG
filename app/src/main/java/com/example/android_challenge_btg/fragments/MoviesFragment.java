package com.example.android_challenge_btg.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_challenge_btg.R;
import com.example.android_challenge_btg.adapters.MoviesListAdapter;
import com.example.android_challenge_btg.callbacks.APIServiceGenreCallback;
import com.example.android_challenge_btg.callbacks.APIServiceMovieCallback;
import com.example.android_challenge_btg.models.Genre;
import com.example.android_challenge_btg.models.Movie;
import com.example.android_challenge_btg.services.APIService;
import com.example.android_challenge_btg.services.DataBaseService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MoviesFragment extends Fragment {


    @BindView(R.id.movie_list_rv)
    RecyclerView movieListRv;

    private View view;
    APIService apiService;
    private MoviesListAdapter moviesListAdapter;
    private List<Movie> movies;
    private boolean favorites;

    public MoviesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.movies = new ArrayList<>();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(favorites){
            movies = DataBaseService.getInstance(getContext()).getRuntimeExceptionDao(Movie.class).queryForAll();
            initAdapter(movies);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view =  inflater.inflate(R.layout.fragment_movies, container, false);

        this.apiService = new APIService(getContext());
        ButterKnife.bind(this, this.view);

        getMovies();

        return this.view;
    }

    private void getMovies() {
        apiService.requestGenres(new APIServiceGenreCallback() {
            @Override
            public void onSuccess(List<Genre> genres) {
                DataBaseService.getInstance(getContext()).createGenres(genres);
                if(favorites){
                    movies = DataBaseService.getInstance(getContext()).getRuntimeExceptionDao(Movie.class).queryForAll();
                    initAdapter(movies);
                } else {
                    apiService.requestMovies(new APIServiceMovieCallback() {
                        @Override
                        public void onSuccess(List<Movie> moviesResp) {
                            movies = moviesResp;
                            initAdapter(movies);
                        }

                        @SuppressLint("ShowToast")
                        @Override
                        public void onError(String message) {
                            Toast.makeText(getContext(), "Error in get movies", Toast.LENGTH_LONG);
                        }
                    });
                }
            }

            @SuppressLint("ShowToast")
            @Override
            public void onError(String message) {
                Toast.makeText(getContext(), "Error in get genres", Toast.LENGTH_LONG);

            }
        });
    }

    private void initAdapter(List<Movie> movies) {
        this.movies = movies;
        this.movieListRv.setLayoutManager(new LinearLayoutManager(getContext()));
        this.moviesListAdapter = new MoviesListAdapter(movies, getContext());
        this.movieListRv.setAdapter(this.moviesListAdapter);
    }


    public void setFavorites(boolean favorites) {
        this.favorites = favorites;
    }
}

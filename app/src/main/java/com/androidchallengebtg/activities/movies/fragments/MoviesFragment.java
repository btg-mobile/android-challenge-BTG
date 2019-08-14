package com.androidchallengebtg.activities.movies.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidchallengebtg.R;
import com.androidchallengebtg.activities.movies.controllers.MoviesController;

import org.json.JSONObject;

public class MoviesFragment extends Fragment {

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        MoviesController moviesController = new MoviesController(new MoviesController.Listener() {
            @Override
            public void onSuccess(JSONObject response) {

            }

            @Override
            public void onError(String message) {
                Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
            }
        });

        moviesController.getListMovies();

        return view;
    }
}

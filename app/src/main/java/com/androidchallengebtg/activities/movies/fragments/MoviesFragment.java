package com.androidchallengebtg.activities.movies.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidchallengebtg.R;
import com.androidchallengebtg.activities.movies.adapters.movies.MoviesAdapter;
import com.androidchallengebtg.activities.movies.controllers.MoviesController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MoviesFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        final MoviesAdapter adapter = new MoviesAdapter(getContext());

        RecyclerView recyclerView =  view.findViewById(R.id.recyclerViewMovies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        MoviesController moviesController = new MoviesController(new MoviesController.Listener() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    JSONArray movies = response.getJSONArray("results");
                    adapter.setList(movies);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

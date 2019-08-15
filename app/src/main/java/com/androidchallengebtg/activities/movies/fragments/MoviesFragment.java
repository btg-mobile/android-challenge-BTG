package com.androidchallengebtg.activities.movies.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidchallengebtg.R;
import com.androidchallengebtg.activities.movieDetail.MovieDetailsActivity;
import com.androidchallengebtg.activities.movies.adapters.movies.MoviesAdapter;
import com.androidchallengebtg.activities.movies.controllers.MoviesController;
import com.androidchallengebtg.helpers.interfaces.EndlessScrollListener;
import com.androidchallengebtg.helpers.interfaces.ItemViewHolderClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MoviesFragment extends Fragment {

    private int currentPage = 1;
    private int totalPages = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        if(getContext()!=null){
            RecyclerView recyclerView =  view.findViewById(R.id.recyclerViewMovies);
            final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipeRefreshMovies);
            final MoviesAdapter adapter = new MoviesAdapter(getContext());

            final MoviesController moviesController = new MoviesController(new MoviesController.Listener() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        MoviesFragment.this.currentPage = response.getInt("page");
                        MoviesFragment.this.totalPages = response.getInt("total_pages");
                        JSONArray movies = response.getJSONArray("results");

                        if(MoviesFragment.this.currentPage == 1){
                            adapter.setList(movies);
                        }else{
                            adapter.putList(movies);
                        }

                        swipeRefreshLayout.setRefreshing(false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String message) {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
                }
            });

            adapter.setClickListener(new ItemViewHolderClickListener() {
                @Override
                public void onClick(int position) {
                    JSONObject jsonObject = adapter.getItem(position);
                    goToDetailsScreen(jsonObject);
                }

                @Override
                public void onLongClick(int position) {

                }
            });

            adapter.setEndlessScrollListener(new EndlessScrollListener() {
                @Override
                public void onEndReached(int position) {
                    Log.e("fim da lista",String.valueOf(position));
                    if(MoviesFragment.this.currentPage < MoviesFragment.this.totalPages){
                        moviesController.getListMovies(MoviesFragment.this.currentPage+1);
                    }
                }
            });

            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);



            swipeRefreshLayout.setRefreshing(true);
            moviesController.getListMovies(1);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    moviesController.getListMovies(1);
                }
            });
        }

        return view;
    }

    private void goToDetailsScreen(JSONObject jsonObject){
        if(getContext()!=null){
            Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
            intent.putExtra("movie",jsonObject.toString());
            getContext().startActivity(intent);
        }
    }
}

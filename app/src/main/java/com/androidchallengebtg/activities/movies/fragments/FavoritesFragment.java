package com.androidchallengebtg.activities.movies.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidchallengebtg.R;
import com.androidchallengebtg.activities.movieDetail.MovieDetailsActivity;
import com.androidchallengebtg.activities.movies.adapters.movies.MoviesAdapter;
import com.androidchallengebtg.activities.movies.controllers.MoviesController;
import com.androidchallengebtg.helpers.EventBus;
import com.androidchallengebtg.helpers.interfaces.EndlessScrollListener;
import com.androidchallengebtg.helpers.interfaces.ItemViewHolderClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FavoritesFragment extends Fragment implements EventBus.EventBusListener {

    private int currentPage = 1;
    private int totalPages = 1;
    private MoviesController moviesController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getInstance().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getInstance().unRegister(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        if(getContext()!=null){
            RecyclerView recyclerView =  view.findViewById(R.id.recyclerViewFavorites);
            final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipeRefreshFavorites);
            final TextView emptyListMessage = view.findViewById(R.id.emptyListMessage);
            emptyListMessage.setVisibility(View.GONE);

            swipeRefreshLayout.setRefreshing(true);

            final MoviesAdapter adapter = new MoviesAdapter(getContext());

            moviesController = new MoviesController(new MoviesController.Listener() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        FavoritesFragment.this.currentPage = response.getInt("page");
                        FavoritesFragment.this.totalPages = response.getInt("total_pages");
                        JSONArray movies = response.getJSONArray("results");

                        if(movies.length()>0){
                            emptyListMessage.setVisibility(View.GONE);
                            if(FavoritesFragment.this.currentPage == 1){
                                adapter.setList(movies);
                            }else{
                                adapter.putList(movies);
                            }
                        }else{
                            adapter.clearList();
                            emptyListMessage.setVisibility(View.VISIBLE);
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
                    if(FavoritesFragment.this.currentPage < FavoritesFragment.this.totalPages){
                        moviesController.getFavoriteMovies(FavoritesFragment.this.currentPage+1);
                    }
                }
            });

            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);

            moviesController.getFavoriteMovies(1);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    moviesController.getFavoriteMovies(1);
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

    @Override
    public void onEvent(Object object) {
        if(moviesController!=null){
            moviesController.getFavoriteMovies(1);
        }
    }
}

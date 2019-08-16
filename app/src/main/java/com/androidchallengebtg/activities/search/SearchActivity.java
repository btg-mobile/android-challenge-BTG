package com.androidchallengebtg.activities.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.androidchallengebtg.R;
import com.androidchallengebtg.activities.BaseActivity;
import com.androidchallengebtg.activities.movieDetail.MovieDetailsActivity;
import com.androidchallengebtg.activities.movies.adapters.movies.MoviesAdapter;
import com.androidchallengebtg.activities.search.contollers.SearchController;
import com.androidchallengebtg.helpers.EventBus;
import com.androidchallengebtg.helpers.interfaces.EndlessScrollListener;
import com.androidchallengebtg.helpers.interfaces.ItemViewHolderClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchActivity extends BaseActivity implements SearchView.OnQueryTextListener {

    private SearchController searchController;
    private int currentPage = 1;
    private int totalPages = 1;
    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.search_title));
            getSupportActionBar().setSubtitle(getString(R.string.search_hint));
        }

        if(getIntent().getExtras()!=null){
            this.query = getIntent().getExtras().getString("query",null);
        }

        RecyclerView recyclerView =  findViewById(R.id.recyclerViewMovies);
        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeRefreshMovies);
        final TextView emptyListMessage = findViewById(R.id.emptyListMessage);
        emptyListMessage.setVisibility(View.GONE);

        swipeRefreshLayout.setRefreshing(true);

        final MoviesAdapter adapter = new MoviesAdapter(this);

        searchController = new SearchController(new SearchController.Listener() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    SearchActivity.this.currentPage = response.getInt("page");
                    SearchActivity.this.totalPages = response.getInt("total_pages");
                    JSONArray movies = response.getJSONArray("results");

                    if(movies.length()>0){
                        emptyListMessage.setVisibility(View.GONE);
                        if(SearchActivity.this.currentPage == 1){
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
                showToast(message);
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
                try {
                    JSONObject movie = adapter.getItem(position);
                    int id = movie.getInt("id");
                    searchController.markAsFavorite(id, true, new SearchController.FavoriteListener() {
                        @Override
                        public void onSuccess(JSONObject response) {

                            try {
                                JSONObject event = new JSONObject();
                                event.put("reload_favorites",true);
                                event.put("reload_popular",false);
                                EventBus.getInstance().emit(event);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            showToast(SearchActivity.this.getString(R.string.added_to_your_favorites));
                        }

                        @Override
                        public void onError(String message) {
                            showToast(message);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        adapter.setEndlessScrollListener(new EndlessScrollListener() {
            @Override
            public void onEndReached(int position) {
                if(SearchActivity.this.currentPage < SearchActivity.this.totalPages){
                    search(SearchActivity.this.query,SearchActivity.this.currentPage+1);
                }
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        search(this.query,1);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                search(SearchActivity.this.query,1);
            }
        });
    }

    private void search(String query, int page){
        searchController.search(query,page);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_view, menu);

        MenuItem item = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) menu.findItem(R.id.search)
                .getActionView();

        searchView.setQueryHint(this.getString(R.string.search_hint));

        searchView.setOnQueryTextListener(this);

        searchView.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        searchView.setOnQueryTextListener(this);

        item.expandActionView();

        searchView.setQuery(this.query, false);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        search(query,1);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        search(query,1);
        return false;
    }

    private void goToDetailsScreen(JSONObject jsonObject){
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("movie",jsonObject.toString());
        startActivity(intent);
    }
}

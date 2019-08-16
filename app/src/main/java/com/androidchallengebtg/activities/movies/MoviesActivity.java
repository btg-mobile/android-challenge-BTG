package com.androidchallengebtg.activities.movies;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.androidchallengebtg.R;
import com.androidchallengebtg.activities.BaseActivity;
import com.androidchallengebtg.activities.movies.adapters.PageAdapter;
import com.androidchallengebtg.activities.movies.fragments.FavoritesFragment;
import com.androidchallengebtg.activities.movies.fragments.MoviesFragment;
import com.androidchallengebtg.activities.search.SearchActivity;
import com.androidchallengebtg.helpers.storage.PrefManager;

public class MoviesActivity extends BaseActivity implements SearchView.OnQueryTextListener {

    private boolean doubleBackToExitPressedOnce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        PageAdapter adapter = new PageAdapter( getSupportFragmentManager() );
        adapter.addPage( new MoviesFragment() , getString(R.string.tab_movies_title));
        adapter.addPage( new FavoritesFragment(), getString(R.string.tab_favorites_title));

        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        findViewById(R.id.tv_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
        }

        this.doubleBackToExitPressedOnce = true;
        showToast(getString(R.string.press_again_to_leave));

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_view, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.search)
                .getActionView();

        searchView.setQueryHint(this.getString(R.string.search_hint));

        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        goToSearch(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        return false;
    }

    private void goToSearch(String query){
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("query",query);
        startActivity(intent);
    }
}

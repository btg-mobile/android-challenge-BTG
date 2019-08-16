package com.androidchallengebtg.activities.movies;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.support.v7.widget.SearchView;

import com.androidchallengebtg.R;
import com.androidchallengebtg.activities.BaseActivity;
import com.androidchallengebtg.activities.movies.adapters.PageAdapter;
import com.androidchallengebtg.activities.movies.fragments.FavoritesFragment;
import com.androidchallengebtg.activities.movies.fragments.MoviesFragment;
import com.androidchallengebtg.activities.search.SearchActivity;

public class MoviesActivity extends BaseActivity implements SearchView.OnQueryTextListener {

    private boolean doubleBackToExitPressedOnce;
    private int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        PageAdapter adapter = new PageAdapter( getSupportFragmentManager() );
        adapter.addPage( new MoviesFragment() , getString(R.string.tab_movies_title));
        adapter.addPage( new FavoritesFragment(), getString(R.string.tab_favorites_title));

        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                MoviesActivity.this.currentPage = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
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

package com.androidchallengebtg.activities.movies;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.androidchallengebtg.R;
import com.androidchallengebtg.activities.BaseActivity;
import com.androidchallengebtg.activities.movies.adapters.PageAdapter;
import com.androidchallengebtg.activities.movies.fragments.FavoritesFragment;
import com.androidchallengebtg.activities.movies.fragments.MoviesFragment;

public class MoviesActivity extends BaseActivity {

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
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getString(R.string.press_again_to_leave), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}

package com.androidchallengebtg.activities.movies;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.androidchallengebtg.R;
import com.androidchallengebtg.activities.BaseActivity;
import com.androidchallengebtg.activities.movies.adapters.PageAdapter;
import com.androidchallengebtg.activities.movies.fragments.FavoritesFragment;
import com.androidchallengebtg.activities.movies.fragments.MoviesFragment;

public class MoviesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        PageAdapter adapter = new PageAdapter( getSupportFragmentManager() );
        adapter.addPage( new MoviesFragment() , "Filmes");
        adapter.addPage( new FavoritesFragment(), "Favoritos");

        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }
}

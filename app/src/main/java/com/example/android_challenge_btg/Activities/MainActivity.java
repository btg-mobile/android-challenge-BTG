package com.example.android_challenge_btg.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.android_challenge_btg.R;
import com.example.android_challenge_btg.ViewPagerAdapter;
import com.example.android_challenge_btg.fragments.MoviesFragment;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tabCategories)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupViewPager();
    }

    private void setupViewPager(){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        MoviesFragment popularMoviesFragment = new MoviesFragment();
        MoviesFragment favoritesFragment = new MoviesFragment();
        popularMoviesFragment.setFavorites(false);
        favoritesFragment.setFavorites(true);
        adapter.addFragment(popularMoviesFragment, "Movies");
        adapter.addFragment(favoritesFragment, "Favorites");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}

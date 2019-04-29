package com.dacruzl2.btgdesafio.presenter.inter;

import android.content.Context;

import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.dacruzl2.btgdesafio.model.pojos.pojoteste.Movie;
import com.dacruzl2.btgdesafio.view.activity.SearchFilmesAdapter;
import com.dacruzl2.btgdesafio.view.inter.IMainActivityView;

import java.util.List;

public interface IMainAPresenter {
    void retrieveSearchMoviesWithQuery(Context context, String movie);

    void searchMoviesWithQuery(SearchView searchView, RecyclerView rvSearchMovies,
                               List<Movie> filmesList, Context context,
                               SearchFilmesAdapter searchFilmesAdapter, ConstraintLayout constraintLayout);

    void onAttachView(IMainActivityView view);

    void onDetachView();

    void configViewPagerScrollAndClick(ViewPager viewPager);

}

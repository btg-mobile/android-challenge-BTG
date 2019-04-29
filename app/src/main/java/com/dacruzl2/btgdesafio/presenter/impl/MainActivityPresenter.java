package com.dacruzl2.btgdesafio.presenter.impl;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.dacruzl2.btgdesafio.R;
import com.dacruzl2.btgdesafio.model.impl.MainActivityModel;
import com.dacruzl2.btgdesafio.model.impl.RetrofitManager;
import com.dacruzl2.btgdesafio.model.inter.IMainAcitivityModel;
import com.dacruzl2.btgdesafio.model.inter.IRetrofitManager;
import com.dacruzl2.btgdesafio.model.pojos.pojoteste.Movie;
import com.dacruzl2.btgdesafio.model.pojos.pojoteste.Root;
import com.dacruzl2.btgdesafio.presenter.inter.IMainAPresenter;
import com.dacruzl2.btgdesafio.view.activity.SearchFilmesAdapter;
import com.dacruzl2.btgdesafio.view.inter.IMainActivityView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPresenter implements IMainAPresenter {
    private IMainActivityView mIMainActivityView;
    private IMainAcitivityModel mIMainAcitivityModel;
    private IRetrofitManager iRetrofitManager;


    public MainActivityPresenter(IMainActivityView aIMainActivityView) {
        mIMainActivityView = aIMainActivityView;
        mIMainAcitivityModel = new MainActivityModel();
        iRetrofitManager = new RetrofitManager();
    }

    @Override
    public void retrieveSearchMoviesWithQuery(Context context, String query) {
        iRetrofitManager.conn(context);
        iRetrofitManager.retrieveMoviesBySearch(RetrofitManager.API_KEY, 1, query)
                .enqueue(new Callback<Root>() {
                    @Override
                    public void onResponse(@NonNull Call<Root> call, @NonNull Response<Root> root) {
                        if (root.isSuccessful()) {
                            mIMainActivityView.addNewItemSearchToFeed(root.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Root> call, @NonNull Throwable t) {

                    }
                });
    }

    @Override
    public void searchMoviesWithQuery(SearchView searchView, RecyclerView rvSearchMovies,
                                      List<Movie> filmesList, Context context,
                                      SearchFilmesAdapter searchFilmesAdapter, ConstraintLayout constraintLayout) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                if (query.length() >= 4) {
                    mIMainActivityView.setupToolBarForSearch();
                    mIMainActivityView.hideViewPager();
                    filmesList.clear();
                    retrieveSearchMoviesWithQuery(context, query);
                    rvSearchMovies.setVisibility(View.VISIBLE);

                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(constraintLayout);
                    constraintSet.connect(R.id.searchViewFilmes, ConstraintSet.TOP,R.id.layout_MainActivity
                             ,ConstraintSet.TOP,170);
                    constraintSet.applyTo(constraintLayout);

                } else if (query.length() < 3) {
                    mIMainActivityView.setupToolBarMain();
                    filmesList.clear();
                    searchFilmesAdapter.notifyDataSetChanged();
                    mIMainActivityView.showViewPager();
                    rvSearchMovies.setVisibility(View.INVISIBLE);

                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(constraintLayout);
                    constraintSet.connect(R.id.searchViewFilmes, ConstraintSet.TOP, R.id.viewpagertab,
                            ConstraintSet.BOTTOM, 4);
                    constraintSet.applyTo(constraintLayout);
                }
                return true;
            }
        });

    }

    @Override
    public void onAttachView(IMainActivityView view) {
        mIMainActivityView = view;
    }

    @Override
    public void onDetachView() {
        mIMainActivityView = null;
    }

    @Override
    public void configViewPagerScrollAndClick(ViewPager viewPager) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    mIMainActivityView.hideSearchFavoritos();
                    mIMainActivityView.showSearchFilmes();
                } else {
                    mIMainActivityView.hideSearchFilmes();
                    mIMainActivityView.showSearchFavoritos();
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    mIMainActivityView.hideSearchFilmes();
                    mIMainActivityView.showSearchFavoritos();
                } else {
                    mIMainActivityView.hideSearchFavoritos();
                    mIMainActivityView.showSearchFilmes();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
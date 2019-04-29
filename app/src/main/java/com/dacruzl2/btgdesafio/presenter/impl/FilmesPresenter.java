package com.dacruzl2.btgdesafio.presenter.impl;

import android.content.Context;
import android.widget.AbsListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.dacruzl2.btgdesafio.model.impl.FilmesModel;
import com.dacruzl2.btgdesafio.model.impl.RetrofitManager;
import com.dacruzl2.btgdesafio.model.inter.IFilmesModel;
import com.dacruzl2.btgdesafio.model.inter.IRetrofitManager;
import com.dacruzl2.btgdesafio.model.pojos.RootGenres;
import com.dacruzl2.btgdesafio.model.pojos.pojoteste.Movie;
import com.dacruzl2.btgdesafio.model.pojos.pojoteste.Root;
import com.dacruzl2.btgdesafio.presenter.inter.IFilmesFPresenter;
import com.dacruzl2.btgdesafio.view.inter.IFilmesView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilmesPresenter implements IFilmesFPresenter {
    private IFilmesView mIFilmesView;
    private IFilmesModel mIFilmesModel;
    private IRetrofitManager iRetrofitManager;

    private int currentItems;
    private int totalItems;
    private int scrollOutItems;
    private Boolean isScrolling = false;
    private int page = 1;

    public FilmesPresenter(IFilmesView fIFilmesView) {
        mIFilmesView = fIFilmesView;
        mIFilmesModel = new FilmesModel();
        iRetrofitManager = new RetrofitManager();
    }

    @Override
    public void onAttachView(IFilmesView view) {
        mIFilmesView = view;
    }

    @Override
    public void onDetachView() {
        mIFilmesView = null;
    }


    @Override
    public void retrieveGenre(Context context) {

        iRetrofitManager.conn(context);
        iRetrofitManager.getGenres(RetrofitManager.API_KEY, "pt")
                .enqueue(new Callback<RootGenres>() {
                    @Override
                    public void onResponse(Call<RootGenres> call, Response<RootGenres> rootGenresResponse) {

                        if (rootGenresResponse.isSuccessful()) {
                            mIFilmesView.addIdMovieForGetGenres(rootGenresResponse.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<RootGenres> call, Throwable t) {

                    }
                });

    }

    @Override
    public void retrievePopularMovies(Context context) {
        mIFilmesView.showRefresh();
        iRetrofitManager.conn(context);
        iRetrofitManager.retrievePopularMovies(RetrofitManager.API_KEY, page, "pt-br")
                .enqueue(new Callback<Root>() {
                    @Override
                    public void onResponse(@NonNull Call<Root> call, @NonNull Response<Root> root) {
                        page++;
                        if (root.isSuccessful()) {
                            mIFilmesView.addNewItemToFeed(root.body());
                            mIFilmesView.hideRefresh();
                        }
                        mIFilmesView.hideRefresh();
                    }

                    @Override
                    public void onFailure(@NonNull Call<Root> call, @NonNull Throwable t) {
                        mIFilmesView.hideRefresh();
                    }
                });
    }

    @Override
    public void configEndlessScroll(RecyclerView recyclerView, Context context,
                                    StaggeredGridLayoutManager staggeredGridLayoutManager) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = staggeredGridLayoutManager.getChildCount();
                totalItems = staggeredGridLayoutManager.getItemCount();
                scrollOutItems = staggeredGridLayoutManager.findFirstVisibleItemPositions(null)[0];

                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
                    isScrolling = false;
                    retrievePopularMovies(context);
                }
            }
        });

    }

    @Override
    public void refreshLayout(List<Movie> movieList, Context context) {
        movieList.clear();
        page = 1;
        if (mIFilmesModel.isNetworkAvailable(context)) {
            retrievePopularMovies(context);
        }

    }



}

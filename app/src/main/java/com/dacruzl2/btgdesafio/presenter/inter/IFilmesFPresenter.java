package com.dacruzl2.btgdesafio.presenter.inter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.dacruzl2.btgdesafio.model.pojos.pojoteste.Movie;
import com.dacruzl2.btgdesafio.view.inter.IFilmesView;

import java.util.List;

public interface IFilmesFPresenter {

    void onAttachView(IFilmesView view);

    void onDetachView();

    void retrievePopularMovies(Context context);

    void configEndlessScroll(RecyclerView recyclerView, Context context,
                              StaggeredGridLayoutManager staggeredGridLayoutManager);

    void refreshLayout(List<Movie> movieList, Context context);

    void retrieveGenre(Context context);

}

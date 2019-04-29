package com.dacruzl2.btgdesafio.view.inter;

import com.dacruzl2.btgdesafio.model.pojos.Genre;
import com.dacruzl2.btgdesafio.model.pojos.RootGenres;
import com.dacruzl2.btgdesafio.model.pojos.pojoteste.Root;

public interface IFilmesView {
    void addNewItemToFeed(Root root);

    void addIdMovieForGetGenres(RootGenres Genre);

    void hideRefresh();

    void showRefresh();

    void showProgressBar();

    void hideProgressBar();

}

package com.dacruzl2.btgdesafio.view.inter;

import android.view.View;

import com.dacruzl2.btgdesafio.model.pojos.pojoteste.Root;

public interface IMainActivityView {

    void hideKeyboard(View view);

    void showSearchFilmes();

    void showSearchFavoritos();

    void hideSearchFilmes();

    void hideSearchFavoritos();

    void hideViewPager();

    void showViewPager();

    void addNewItemSearchToFeed(Root root);

    void setupToolBarForSearch();

    void setupToolBarMain();
}

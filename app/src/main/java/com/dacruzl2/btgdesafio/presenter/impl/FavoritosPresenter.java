package com.dacruzl2.btgdesafio.presenter.impl;

import com.dacruzl2.btgdesafio.model.impl.FavoritosModel;
import com.dacruzl2.btgdesafio.model.inter.IFavoritosModel;
import com.dacruzl2.btgdesafio.presenter.inter.IFavoritosFPresenter;
import com.dacruzl2.btgdesafio.view.inter.IFavoritosView;

public class FavoritosPresenter implements IFavoritosFPresenter {
    private IFavoritosView mIFavoritosView;
    private IFavoritosModel mIFavoritosFModel;

    public FavoritosPresenter(IFavoritosView fIFavoritosView) {
        mIFavoritosView = fIFavoritosView;
        mIFavoritosFModel = new FavoritosModel();
    }

    @Override
    public void onAttachView(IFavoritosView view) {
        mIFavoritosView = view;
    }

    @Override
    public void onDetachView() {
    mIFavoritosView = null;
    }
}

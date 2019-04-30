package com.dacruzl2.btgdesafio.presenter.impl;

import com.dacruzl2.btgdesafio.model.impl.DetailMovieAModelImpl;
import com.dacruzl2.btgdesafio.model.inter.IDetailMovieAModel;
import com.dacruzl2.btgdesafio.presenter.inter.IDetailMovieAPresenter;
import com.dacruzl2.btgdesafio.view.inter.IDetailMovieAView;

public class DetailMovieAPresenterImpl implements IDetailMovieAPresenter {
    private IDetailMovieAView mIDetailMovieAView;
    private IDetailMovieAModel mIDetailMovieAModel;

    public DetailMovieAPresenterImpl(IDetailMovieAView aIDetailMovieAView) {
        mIDetailMovieAView = aIDetailMovieAView;
        mIDetailMovieAModel = new DetailMovieAModelImpl();
    }

}

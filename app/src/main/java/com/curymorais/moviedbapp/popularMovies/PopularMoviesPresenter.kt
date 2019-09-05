package com.curymorais.twitchtop100

import com.curymorais.moviedbapp.Contract
import com.curymorais.moviedbapp.data.domain.Movie

class PopularMoviesPresenter (view : Contract.BaseView?, interactor: PopularMoviesInteractor) : Contract.BasePresenter, Contract.BaseInteractor.OnFinishedListener {

    var mView: Contract.BaseView? = view
    var mInteractor = interactor

    override fun getData() {
        mView?.showProgress()
        mInteractor.getMovieArrayList(this)
    }

    override fun onSuccess(movieArrayList: ArrayList<Movie>?) {
        mView?.hideProgress()
        mView?.updateView(movieArrayList)
    }

    override fun onFailure(t: Throwable) {
        mView?.hideProgress()
        mView?.getDataFail(t.message)
    }

    fun onDestroy() {
        mView = null
    }

}
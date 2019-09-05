package com.curymorais.moviedbapp

import com.curymorais.moviedbapp.data.domain.Movie


interface Contract {

    interface BaseView {
        fun updateData()
        fun updateView(gameList : ArrayList<Movie>?)
        fun getDataSuccess()
        fun getDataFail(msg: String?)
        fun showProgress()
        fun hideProgress()
    }

    interface BasePresenter {
        fun getData()
    }

    interface BaseInteractor {

        interface OnFinishedListener {
            //            void onFinished(ArrayList<Notice> noticeArrayList);
            fun onSuccess(gameArrayList: ArrayList<Movie>?)

            fun onFailure(t: Throwable)
        }

        fun getMovieArrayList(onFinishedListener: OnFinishedListener)
    }
}
package com.desafio.marvelapp.detailComic

import com.desafio.marvelapp.data.IDataManager
import com.desafio.marvelapp.detailCharacter.DetailComicView
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class DetailComicPresenterImpl(private val view: DetailComicView, private val dataManager: IDataManager) : DetailComicPresenter {

    private val diposable = CompositeDisposable()

    override fun getMarvelComic(id: Int) {
        view.showProgressBar()
        diposable.add(dataManager.loadDetailComicCharacter(id)
                .subscribe({ comic ->
                    view.hideProgressBar()
                    view.loadComic(comic)
                }, { error ->
                    view.error()
                    Timber.e("error", error.message)
                }))

    }

    override fun onDisposable() {
        diposable.clear()
    }
}
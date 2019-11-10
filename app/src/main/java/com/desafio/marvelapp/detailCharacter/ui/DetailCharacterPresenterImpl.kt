package com.desafio.marvelapp.detailCharacter.ui

import com.desafio.marvelapp.data.IDataManager
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class DetailCharacterPresenterImpl(private val view: DetailCharacterView,
                                   private val dataManager: IDataManager) : DetailCharacterPresenter {

    private val disposable = CompositeDisposable()

    override fun getMarvelCharacter(id: Int) {
        view.showProgressBar()
        disposable.add(dataManager.loadDetailMarvelCharacter(id)
                .subscribe({ character ->
                    view.hideProgressBar()
                    view.loadCharacter(character)
                }, { error ->
                    view.hideProgressBar()
                    view.error()
                    Timber.e("error", error.message)
                }))

    }

    override fun onDisposable() {
        disposable.clear()
    }
}
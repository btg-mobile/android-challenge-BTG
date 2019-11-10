package com.desafio.marvelapp.data.repository

import com.desafio.marvelapp.model.Favorite
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class RepositoryHelper(private val favoriteDao: FavoriteDao) : IRepositoryData {

    override fun getAllFavorites(): Flowable<List<Favorite>> {
        return favoriteDao.getAll()
    }

    override fun insertFavorite(favorite: Favorite) {
        Single.fromCallable({ favoriteDao.insert(favorite) })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ Timber.d("Success") },
                        { error -> Timber.w("Error>$error") })
    }

    override fun deleteFavorite(favorite: Favorite): Single<Unit> =
        Single.fromCallable { favoriteDao.delete(favorite) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

    override fun getCharactersFavorites(): Flowable<List<Favorite>> {
        return favoriteDao.getCharactersFavorites()
    }

    override fun getComicsFavorites(): Flowable<List<Favorite>> {
        return favoriteDao.getComicsFavorites()
    }
}
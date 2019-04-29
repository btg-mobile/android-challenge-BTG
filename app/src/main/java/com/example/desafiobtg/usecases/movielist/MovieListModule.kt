package com.example.desafiobtg.usecases.movielist

import com.example.desafiobtg.di.qualifiers.ActivityScoped
import com.example.desafiobtg.di.qualifiers.FragmentScoped
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Named

@Module
abstract class MovieListModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun favoriteListView(): FavoriteListFragment

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun movieListView(): MovieListFragment

    @ActivityScoped
    @Binds
    @Named("FavoriteListPresenter")
    abstract fun favoriteListPresenter(presenter: MovieListPresenter): MovieListContract.Presenter

    @ActivityScoped
    @Binds
    @Named("MovieListPresenter")
    abstract fun movieListPresenter(presenter: MovieListPresenter): MovieListContract.Presenter

}
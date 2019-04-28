package com.example.desafiobtg.usecases.movielist

import com.example.desafiobtg.di.qualifiers.ActivityScoped
import com.example.desafiobtg.di.qualifiers.FragmentScoped
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MovieListModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun movieListView(): MovieListFragment

    @ActivityScoped
    @Binds
    abstract fun movieListPresenter(presenter: MovieListPresenter): MovieListContract.Presenter

}
package com.example.desafiobtg.usecases.moviedetails

import com.example.desafiobtg.di.qualifiers.ActivityScoped
import com.example.desafiobtg.di.qualifiers.FragmentScoped
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MovieDetailsModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun movieDetailsView(): MovieDetailsFragment

    @ActivityScoped
    @Binds
    abstract fun movieDetailsPresenter(presenter: MovieDetailsPresenter): MovieDetailsContract.Presenter

}
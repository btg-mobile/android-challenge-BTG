package com.example.desafiobtg.di

import com.example.desafiobtg.di.qualifiers.ActivityScoped
import com.example.desafiobtg.usecases.MainActivity
import com.example.desafiobtg.usecases.MainModule
import com.example.desafiobtg.usecases.moviedetails.MovieDetailsActivity
import com.example.desafiobtg.usecases.moviedetails.MovieDetailsModule
import com.example.desafiobtg.usecases.movielist.MovieListModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainModule::class, MovieListModule::class])
    internal abstract fun mainActivity(): MainActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MovieDetailsModule::class])
    internal abstract fun movieDetailsActivity(): MovieDetailsActivity

}
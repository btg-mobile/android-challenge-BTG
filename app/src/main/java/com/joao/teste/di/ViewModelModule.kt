package com.joao.teste.di

import com.joao.teste.presentation.MainViewModel
import com.joao.teste.presentation.detail.DetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelModule {
    val viewModelModules = module {
        viewModel { MainViewModel(get()) }
        viewModel { DetailViewModel(get()) }
    }
}
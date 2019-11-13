package com.joao.teste.di

import com.joao.domain.usecase.MoviesUseCase
import com.joao.domain.usecase.MoviesUseCaseImpl
import org.koin.dsl.module

object DomainModule {
    val domainModules = module {
        single<MoviesUseCase> { MoviesUseCaseImpl(get()) }
    }
}
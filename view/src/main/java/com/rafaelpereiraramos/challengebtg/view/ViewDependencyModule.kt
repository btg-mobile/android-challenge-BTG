package com.rafaelpereiraramos.challengebtg.view

import com.rafaelpereiraramos.challengebtg.repository.RepositoryDependencyModule
import com.rafaelpereiraramos.challengebtg.view.detail.DetailViewModel
import com.rafaelpereiraramos.challengebtg.view.search.SearchViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

class ViewDependencyModule {

    companion object {
        val viewModelModule = RepositoryDependencyModule.repositoryDependencyModule.plus(
            module {
                viewModel {
                    SearchViewModel(get())
                }

                viewModel {
                    DetailViewModel(get())
                }
            }
        )
    }
}
package com.desafio.marvelapp.data.repository.di

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.desafio.marvelapp.data.DataManager
import com.desafio.marvelapp.data.IDataManager
import com.desafio.marvelapp.data.api.IApiData
import com.desafio.marvelapp.data.api.MarvelApiHelper
import com.desafio.marvelapp.data.repository.IRepositoryData
import com.desafio.marvelapp.data.repository.RepositoryHelper


class DataManagerModule {
    val dependenciesKodein = Kodein.Module {

        bind<IDataManager>() with singleton {
            DataManager(
                    apiData = instance(),
                    repositoryData = instance()
            )
        }

        bind<IApiData>() with singleton {
            MarvelApiHelper()
        }

        bind<IRepositoryData>() with singleton {
            RepositoryHelper(favoriteDao = instance())
        }
    }
}
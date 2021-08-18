package com.shigure.lastfm_api.features.main

import com.shigure.lastfm_api.features.main.interactor.MainUseCase
import com.shigure.lastfm_api.features.main.presentation.MainFragment
import com.shigure.lastfm_api.features.main.presentation.MainViewModel
import com.shigure.lastfm_api.features.main.repository.AlbumsRepository
import com.shigure.lastfm_api.features.main.repository.AlbumsRepositoryImpl
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object FeatureMainModule {

    val mainModule = module {

        single<AlbumsRepository> {
            AlbumsRepositoryImpl(get())
        }

        factory {
            MainUseCase(get())
        }
        viewModel {
            MainViewModel(get())
        }
        fragment {
            MainFragment()
        }
    }

}
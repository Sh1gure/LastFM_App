package com.shigure.lastfm_api.features.albums

import com.shigure.lastfm_api.features.albums.interactor.AlbumsUseCase
import com.shigure.lastfm_api.features.albums.presentation.AlbumsFragment
import com.shigure.lastfm_api.features.albums.presentation.AlbumsViewModel
import com.shigure.lastfm_api.features.albums.repository.AlbumsRepository
import com.shigure.lastfm_api.features.albums.repository.AlbumsRepositoryImpl
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object FeatureAlbumsModule {

    val albumsModule = module {
        single<AlbumsRepository> {
            AlbumsRepositoryImpl(get(), get(), get())
        }
        factory {
            AlbumsUseCase(get())
        }
        viewModel {
            AlbumsViewModel(get(), get())
        }
        fragment {
            AlbumsFragment()
        }
    }

}
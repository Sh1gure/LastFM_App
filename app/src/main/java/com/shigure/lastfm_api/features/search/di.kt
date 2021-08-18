package com.shigure.lastfm_api.features.search

import com.shigure.lastfm_api.features.search.data.repository.SearchRepository
import com.shigure.lastfm_api.features.search.data.repository.SearchRepositoryImpl
import com.shigure.lastfm_api.features.search.interactor.SearchUseCase
import com.shigure.lastfm_api.features.search.presentation.SearchFragment
import com.shigure.lastfm_api.features.search.presentation.SearchViewModel
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object FeatureSearchModules {

    val searchModule = module {
        single<SearchRepository> {
            SearchRepositoryImpl(get(), get(), get())
        }
        factory {
            SearchUseCase(get())
        }
        viewModel {
            SearchViewModel(get(), get())
        }
        fragment {
            SearchFragment()
        }
    }

}
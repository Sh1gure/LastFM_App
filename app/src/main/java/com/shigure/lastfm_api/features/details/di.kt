package com.shigure.lastfm_api.features.details

import com.shigure.lastfm_api.features.details.presentation.DetailsFragment
import com.shigure.lastfm_api.features.details.presentation.DetailsViewModel
import com.shigure.lastfm_api.features.details.repository.DetailsRepository
import com.shigure.lastfm_api.features.details.repository.DetailsRepositoryImpl
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object FeatureDetailsModule {

    val detailsModule = module {
        single<DetailsRepository> {
            DetailsRepositoryImpl(get(), get(), get(), get(), get())
        }
        viewModel {
            DetailsViewModel(get(), get(), get())
        }
        fragment {
            DetailsFragment()
        }
    }

}
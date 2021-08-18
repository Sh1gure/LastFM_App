package com.shigure.lastfm_api

import android.app.Application
import com.shigure.core_network.remote.networkModule
import com.shigure.core_common.resourceProvider.ResourceProviderModule.resourceProviderModule
import com.shigure.core_common.gson.Gson.gsonModule
import com.shigure.core_data_base.DataBaseModule.databaseModule
import com.shigure.lastfm_api.features.albums.FeatureAlbumsModule.albumsModule
import com.shigure.lastfm_api.features.details.FeatureDetailsModule.detailsModule
import com.shigure.lastfm_api.features.main.FeatureMainModule.mainModule
import com.shigure.lastfm_api.features.search.FeatureSearchModules.searchModule
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.KoinExperimentalAPI
import org.koin.core.context.startKoin

@KoinExperimentalAPI
class LastFmApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@LastFmApplication)
            fragmentFactory()
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    resourceProviderModule,
                    gsonModule,
                    searchModule,
                    albumsModule,
                    detailsModule,
                    mainModule
                )
            )
        }
    }

}
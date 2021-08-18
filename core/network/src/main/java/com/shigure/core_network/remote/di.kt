package com.shigure.core_network.remote

import com.shigure.core_network.BuildConfig
import com.shigure.core_network.remote.util.NetworkChecker
import com.shigure.core_network.remote.util.NetworkCheckerImpl
import okhttp3.OkHttpClient
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val CONNECT_TIMEOUT_SEC = 10L

val HOST_TAG = StringQualifier("com.shigure.lastfm_api.net.HostTag")
val OKHTTP_TAG = StringQualifier("com.shigure.lastfm_api.net.OkhttpTag")

val networkModule = module {
    single(HOST_TAG) {
        "https://ws.audioscrobbler.com/"
    }
    single {
        BuildConfig.LAST_FM_KEY
    }

    single(OKHTTP_TAG) {
        createApiOkHttpClient()
    }

    single { createRetrofitClient(get(HOST_TAG), get(OKHTTP_TAG)).create(LastFmRetrofitInterface::class.java) }

    single<NetworkChecker> { NetworkCheckerImpl(get()) }
}


private fun createApiOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(CONNECT_TIMEOUT_SEC, TimeUnit.SECONDS)
        .build()
}

private fun createRetrofitClient(baseUrl: String, client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
}
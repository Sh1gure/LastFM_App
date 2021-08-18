package com.shigure.core_common.resourceProvider

import org.koin.dsl.module

object ResourceProviderModule {

    val resourceProviderModule = module {
        single<ResourceProvider> { ResourceProviderImpl(get()) }
    }

}
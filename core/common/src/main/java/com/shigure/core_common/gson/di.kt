package com.shigure.core_common.gson

import com.google.gson.Gson
import org.koin.dsl.module

object Gson {
    val gsonModule = module {
        single { Gson() }
    }
}
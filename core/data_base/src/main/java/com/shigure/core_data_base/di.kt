package com.shigure.core_data_base

import androidx.room.Room
import com.shigure.core_data_base.database.AlbumsDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module


object DataBaseModule {
    private val ALBUMS_DB_TAG = StringQualifier("com.shigure.lastfm_api.db.AlbumsDbTag")
    val databaseModule = module {

        single {
            Room.databaseBuilder(
                androidApplication(),
                AlbumsDatabase::class.java,
                get(ALBUMS_DB_TAG)
            )
                .build()
        }

        single { get<AlbumsDatabase>().albumsDao() }

        single(ALBUMS_DB_TAG) { "albums-db" }
    }

}

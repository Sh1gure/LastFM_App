package com.shigure.core_data_base.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shigure.core_data_base.converter.TracksListConverter
import com.shigure.core_data_base.dao.AlbumsDao
import com.shigure.core_data_base.entity.AlbumDb

@Database(
    entities = [AlbumDb::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TracksListConverter::class)
abstract class AlbumsDatabase : RoomDatabase() {
    abstract fun albumsDao(): AlbumsDao
}
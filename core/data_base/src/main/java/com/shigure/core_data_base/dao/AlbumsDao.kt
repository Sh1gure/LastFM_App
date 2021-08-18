package com.shigure.core_data_base.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.shigure.core_data_base.entity.AlbumDb
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumsDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(album: AlbumDb)

    @Delete
    suspend fun delete(album: AlbumDb)

    @Query("DELETE FROM albums WHERE artist LIKE :artist AND name LIKE :album")
    suspend fun delete(artist: String, album: String)

    @Query("SELECT * FROM albums WHERE artist LIKE :artist")
    suspend fun getAlbums(artist: String): List<AlbumDb>?

    @Query("SELECT * FROM albums")
    suspend fun getAllAlbums(): List<AlbumDb>?

    @Query("SELECT * FROM albums")
    fun getAlbumsFlow(): Flow<List<AlbumDb>?>

    @Query("SELECT * FROM albums WHERE artist LIKE :artist AND name LIKE :album")
    suspend fun getAlbum(artist: String, album: String): AlbumDb?

}
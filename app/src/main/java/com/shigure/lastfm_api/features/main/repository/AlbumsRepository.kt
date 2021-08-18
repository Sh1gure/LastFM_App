package com.shigure.lastfm_api.features.main.repository

import com.shigure.core_data_base.entity.AlbumDb
import kotlinx.coroutines.flow.Flow

interface AlbumsRepository {

    suspend fun getAlbums(): List<AlbumDb>

    suspend fun getAlbumsFlow(): Flow<List<AlbumDb>?>

    suspend fun deleteAlbum(album: AlbumDb)

}
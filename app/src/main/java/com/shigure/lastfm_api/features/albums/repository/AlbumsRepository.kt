package com.shigure.lastfm_api.features.albums.repository

import com.shigure.core_data_base.entity.AlbumDb
import com.shigure.core_network.model.albums.TopAlbumsResult
import com.shigure.lastfm_api.features.albums.data.model.AlbumCommon

interface AlbumsRepository {

    suspend fun getTopAlbums(artist: String, page: Int? = null, limit: Int? = null): TopAlbumsResult

    suspend fun getSavedAlbums(artist: String): List<AlbumDb>

    suspend fun saveAlbum(album: AlbumCommon)

    suspend fun deleteAlbum(album: AlbumDb)

    suspend fun deleteAlbum(artist: String, album: String)
}
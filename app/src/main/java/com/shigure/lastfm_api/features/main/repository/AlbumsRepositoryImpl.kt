package com.shigure.lastfm_api.features.main.repository

import com.shigure.core_data_base.dao.AlbumsDao
import com.shigure.core_data_base.entity.AlbumDb
import kotlinx.coroutines.flow.Flow

class AlbumsRepositoryImpl(
    private val albumsDao: AlbumsDao
) : AlbumsRepository {

    override suspend fun getAlbums(): List<AlbumDb> =
        albumsDao.getAllAlbums() ?: emptyList()

    override suspend fun getAlbumsFlow(): Flow<List<AlbumDb>?> =
        albumsDao.getAlbumsFlow()

    override suspend fun deleteAlbum(album: AlbumDb) {
        albumsDao.delete(album)
    }
}
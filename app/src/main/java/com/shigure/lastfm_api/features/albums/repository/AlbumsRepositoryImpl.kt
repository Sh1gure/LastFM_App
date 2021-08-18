package com.shigure.lastfm_api.features.albums.repository

import com.shigure.core_data_base.dao.AlbumsDao
import com.shigure.core_data_base.entity.AlbumDb
import com.shigure.core_data_base.entity.TrackDb
import com.shigure.core_network.model.albums.TopAlbumsResult
import com.shigure.core_network.remote.LastFmRetrofitInterface
import com.shigure.lastfm_api.features.albums.data.model.AlbumCommon

class AlbumsRepositoryImpl(
    private val api: LastFmRetrofitInterface,
    private val apiKey: String,
    private val albumsDao: AlbumsDao,
) : AlbumsRepository {

    override suspend fun getTopAlbums(artist: String, page: Int?, limit: Int?): TopAlbumsResult {
        return getTopAlbumsByArtist(artist, page, limit)
    }

    override suspend fun getSavedAlbums(artist: String): List<AlbumDb> {
        return albumsDao.getAlbums(artist) ?: emptyList()
    }

    override suspend fun saveAlbum(album: AlbumCommon) {
        val albumInfoResult =
            api.getAlbumInfo(
                apiKey,
                album.artist.name,
                album.name,
                mbid = null,
                lang = null,
                autoCorrect = null
            )

        val tracks =
            albumInfoResult.albumInfo.tracksResult.tracks.map { TrackDb(it.name, it.duration) }
        val albumDb = AlbumDb(
            album.name,
            album.artist.name,
            album.imageUrl,
            tracks,
            album.playCount
        )

        albumsDao.insert(albumDb)
    }

    override suspend fun deleteAlbum(album: AlbumDb) {
        albumsDao.delete(album)
    }

    override suspend fun deleteAlbum(artist: String, album: String) {
        albumsDao.delete(artist, album)
    }

    private suspend fun getTopAlbumsByArtist(
        artist: String,
        page: Int?,
        limit: Int?
    ): TopAlbumsResult =
        api.getTopAlbums(apiKey, artist = artist, mbid = null, page = page, limit = limit)
}
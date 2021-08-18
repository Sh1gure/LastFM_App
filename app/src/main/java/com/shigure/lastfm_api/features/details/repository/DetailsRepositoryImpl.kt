package com.shigure.lastfm_api.features.details.repository

import android.util.Log
import com.shigure.lastfm_api.R
import com.shigure.core_network.model.getImageUrl
import com.shigure.core_network.remote.LastFmRetrofitInterface
import com.shigure.core_network.remote.ResponseResult
import com.shigure.core_network.remote.util.NetworkChecker
import com.shigure.core_common.resourceProvider.ResourceProvider
import com.shigure.core_data_base.dao.AlbumsDao
import com.shigure.lastfm_api.features.details.data.model.AlbumView
import com.shigure.lastfm_api.features.details.data.model.TrackCommon
import java.net.SocketTimeoutException

class DetailsRepositoryImpl(
    private val dao: AlbumsDao,
    private val api: LastFmRetrofitInterface,
    private val apiKey: String,
    private val networkChecker: NetworkChecker,
    private val resourceProvider: ResourceProvider
) : DetailsRepository {

    override suspend fun getAlbumDetails(artist: String, album: String): ResponseResult<AlbumView> {
        return try {
            val result = if (networkChecker.isNetworkConnected) {
                try {
                    getAlbumsFromApi(artist, album)
                } catch (error: SocketTimeoutException) {
                    getAlbumsFromDB(artist, album)
                }
            } else {
                getAlbumsFromDB(artist, album)
            }
            ResponseResult.Success(result)
        } catch (error: Throwable) {
            ResponseResult.Failure(
                error.localizedMessage ?: resourceProvider.getString(R.string.error_unknown)
            )
        }

    }

    private suspend fun getAlbumsFromApi(artist: String, album: String): AlbumView {
        val albumNet =
            api.getAlbumInfo(
                apiKey,
                artist,
                album,
                mbid = null,
                lang = null,
                autoCorrect = null
            ).albumInfo
        val tracks = try {
            albumNet.tracksResult.tracks.map { TrackCommon(it.name, it.duration) }
        } catch (error: NullPointerException) {
            Log.e("TRAKS", "empty tracks", error)
            listOf()
        }

        return AlbumView(albumNet.name, albumNet.artist, albumNet.images.getImageUrl(), tracks)
    }

    private suspend fun getAlbumsFromDB(artist: String, album: String): AlbumView {
        val albumDb = dao.getAlbum(artist, album)

        return if (albumDb == null) {
            AlbumView(album, artist, "", emptyList())
        } else {
            AlbumView(
                albumDb.name,
                albumDb.artist,
                albumDb.imageUrl,
                albumDb.tracksDb.map { TrackCommon(it.name, it.duration) }
            )
        }
    }

}
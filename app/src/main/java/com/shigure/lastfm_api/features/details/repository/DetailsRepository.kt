package com.shigure.lastfm_api.features.details.repository

import com.shigure.core_network.remote.ResponseResult
import com.shigure.lastfm_api.features.details.data.model.AlbumView

interface DetailsRepository {

    suspend fun getAlbumDetails(artist: String, album: String): ResponseResult<AlbumView>

}
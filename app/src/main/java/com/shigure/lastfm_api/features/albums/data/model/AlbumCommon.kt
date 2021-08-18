package com.shigure.lastfm_api.features.albums.data.model

import com.shigure.core_network.model.albums.AlbumsArtist

data class AlbumCommon(
    val name: String,
    val playCount: Int,
    val url: String,
    val artist: AlbumsArtist,
    val imageUrl: String,
    val isStored: Boolean = false,
    var isBeingProcessed: Boolean = false
)
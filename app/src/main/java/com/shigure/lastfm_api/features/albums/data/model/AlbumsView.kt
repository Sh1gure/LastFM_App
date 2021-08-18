package com.shigure.lastfm_api.features.albums.data.model

data class AlbumsView(
    val artist: String,
    val albums: List<AlbumCommon>,
    val addedCount: Int = 0
)
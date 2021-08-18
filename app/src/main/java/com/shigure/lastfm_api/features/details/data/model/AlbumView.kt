package com.shigure.lastfm_api.features.details.data.model


data class AlbumView(
    val name: String,
    val artist: String,
    val imageUrl: String,
    val tracks: List<TrackCommon>
)
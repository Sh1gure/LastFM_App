package com.shigure.lastfm_api.features.details.data.model

data class DetailsCommon(
    val name: String,
    val artist: String,
    val tracks: List<TrackCommon>,
    val imageUrl: String,
    val isOnline: Boolean
)
package com.shigure.core_network.model.details

import com.google.gson.annotations.SerializedName
import com.shigure.core_network.model.Image

data class AlbumInfo(

    @SerializedName("name")
    val name: String,

    @SerializedName("artist")
    val artist: String,

    @SerializedName("mbid")
    val mbid: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("image")
    val images: List<Image>,

    @SerializedName("listeners")
    val listeners: Int,

    @SerializedName("playCount")
    val playCount: Int,

    @SerializedName("tracks")
    val tracksResult: TracksResult
)
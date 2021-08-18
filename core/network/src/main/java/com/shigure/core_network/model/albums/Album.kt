package com.shigure.core_network.model.albums

import com.google.gson.annotations.SerializedName
import com.shigure.core_network.model.Image

data class Album(

    @SerializedName("name")
    val name: String,

    @SerializedName("playcount")
    val playCount: Int,

    @SerializedName("url")
    val url: String,

    @SerializedName("artist")
    val artist: AlbumsArtist,

    @SerializedName("image")
    val images: List<Image>

)
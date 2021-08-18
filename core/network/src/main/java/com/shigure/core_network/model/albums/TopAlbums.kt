package com.shigure.core_network.model.albums

import com.google.gson.annotations.SerializedName

data class TopAlbums(

    @SerializedName("album")
    val albums: List<Album>,

    @SerializedName("@attr")
    val attributes: TopAlbumsAttributes

)
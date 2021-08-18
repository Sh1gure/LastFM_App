package com.shigure.core_network.model.albums

import com.google.gson.annotations.SerializedName

data class AlbumsArtist(

    @SerializedName("name")
    val name: String,

    @SerializedName("url")
    val url: String

)
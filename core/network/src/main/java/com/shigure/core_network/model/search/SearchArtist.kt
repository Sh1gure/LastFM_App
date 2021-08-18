package com.shigure.core_network.model.search

import com.google.gson.annotations.SerializedName
import com.shigure.core_network.model.Image

data class SearchArtist(

    @SerializedName("name")
    val name: String,

    @SerializedName("listeners")
    val listeners: Int,

    @SerializedName("url")
    val url: String,

    @SerializedName("mbid")
    val mbid: String,

    @SerializedName("streamable")
    val streamable: Boolean,

    @SerializedName("image")
    val images: List<Image>

)
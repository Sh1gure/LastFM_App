package com.shigure.core_network.model.albums

import com.google.gson.annotations.SerializedName

data class TopAlbumsAttributes(

    @SerializedName("artist")
    val artist: String,

    @SerializedName("page")
    val page: Int,

    @SerializedName("perPage")
    val perPage: Int,

    @SerializedName("totalPages")
    val totalPages: Int,

    @SerializedName("total")
    val total: Int
)
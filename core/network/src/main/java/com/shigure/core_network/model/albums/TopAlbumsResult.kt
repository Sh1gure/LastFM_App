package com.shigure.core_network.model.albums

import com.google.gson.annotations.SerializedName

data class TopAlbumsResult(

    @SerializedName("topalbums")
    val topAlbums: TopAlbums

)
package com.shigure.core_network.model.details

import com.google.gson.annotations.SerializedName

data class AlbumInfoResult(

    @SerializedName("album")
    val albumInfo: AlbumInfo

)
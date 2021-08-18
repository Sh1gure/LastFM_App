package com.shigure.core_network.model.details

import com.google.gson.annotations.SerializedName

data class TrackInfo(

    @SerializedName("name")
    val name: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("duration")
    val duration: Long
)
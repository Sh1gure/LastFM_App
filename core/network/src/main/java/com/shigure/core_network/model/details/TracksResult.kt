package com.shigure.core_network.model.details

import com.google.gson.annotations.SerializedName

data class TracksResult(

    @SerializedName("track")
    val tracks: List<TrackInfo>

)
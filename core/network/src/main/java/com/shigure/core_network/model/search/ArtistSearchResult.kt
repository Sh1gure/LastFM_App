package com.shigure.core_network.model.search

import com.google.gson.annotations.SerializedName

data class ArtistSearchResult(

    @SerializedName("results")
    val results: SearchModel

)
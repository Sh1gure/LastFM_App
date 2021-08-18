package com.shigure.core_network.model.search

import com.google.gson.annotations.SerializedName

data class SearchModel(

    @SerializedName("opensearch:Query")
    val query: SearchQuery,

    @SerializedName("opensearch:totalResults")
    val totalResults: Int,

    @SerializedName("opensearch:startIndex")
    val startIndex: Int,

    @SerializedName("opensearch:itemsPerPage")
    val itemsPerPage: Int,

    @SerializedName("artistmatches")
    val artistmatches: ArtistMatches
)
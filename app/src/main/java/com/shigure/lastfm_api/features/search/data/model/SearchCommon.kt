package com.shigure.lastfm_api.features.search.data.model

import com.shigure.core_network.model.search.SearchArtist

data class SearchCommon(
    val searchQuery: String,
    val artists: List<SearchArtist>,
    val newSearch: Boolean,
    val addedCount: Int = 0
)
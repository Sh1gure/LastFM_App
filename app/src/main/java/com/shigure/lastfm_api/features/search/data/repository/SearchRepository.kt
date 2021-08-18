package com.shigure.lastfm_api.features.search.data.repository

import com.shigure.core_network.model.search.ArtistSearchResult
import com.shigure.core_network.remote.ResponseResult

interface SearchRepository {
    suspend fun searchForArtist(
        artistName: String,
        page: Int? = null,
        limit: Int? = null
    ): ResponseResult<ArtistSearchResult>
}
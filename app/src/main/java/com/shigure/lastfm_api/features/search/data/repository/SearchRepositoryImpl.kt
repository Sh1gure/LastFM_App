package com.shigure.lastfm_api.features.search.data.repository

import com.shigure.lastfm_api.R
import com.shigure.core_network.model.search.ArtistSearchResult
import com.shigure.core_network.remote.LastFmRetrofitInterface
import com.shigure.core_network.remote.ResponseResult
import com.shigure.core_common.resourceProvider.ResourceProvider


class SearchRepositoryImpl(
    private val api: LastFmRetrofitInterface,
    private val apiKey: String,
    private val resourceProvider: ResourceProvider,
) : SearchRepository {

    override suspend fun searchForArtist(
        artistName: String,
        page: Int?,
        limit: Int?
    ): ResponseResult<ArtistSearchResult> {
        return try {
            ResponseResult.Success(api.searchForArtist(apiKey, artistName, page, limit))
        } catch (error: Throwable) {
            ResponseResult.Failure(
                error.localizedMessage ?: resourceProvider.getString(R.string.error_unknown)
            )
        }
    }

}
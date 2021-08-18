package com.shigure.lastfm_api.features.search.interactor

import com.shigure.core_network.model.search.SearchArtist
import com.shigure.core_network.remote.ResponseResult
import com.shigure.lastfm_api.features.search.data.model.SearchCommon
import com.shigure.lastfm_api.features.search.data.repository.SearchRepository

class SearchUseCase(
    private val repository: SearchRepository
) {

    private var artists: MutableList<SearchArtist> = mutableListOf()
    private var searchResult: ResponseResult<SearchCommon> = ResponseResult.Failure("")
    private var searchQuery: String = ""
    private var currentPage: Int = 1
    private var maxPages: Int = 0

    suspend fun performNewSearch(searchQuery: String): ResponseResult<SearchCommon> {
        if (searchQuery.isEmpty()) return ResponseResult.Empty()
        this.searchQuery = searchQuery
        loadArtists(true)
        return searchResult
    }

    suspend fun loadMoreArtists(): ResponseResult<SearchCommon> {
        if (currentPage >= maxPages) return ResponseResult.Empty()
        currentPage++
        loadArtists(false)
        return searchResult
    }

    private suspend fun loadArtists(newSearch: Boolean) {

        val page = if (newSearch) 1 else currentPage
        when (val searchModel = repository.searchForArtist(searchQuery, page = page)) {
            is ResponseResult.Success -> {
                val searchResult = searchModel.data.results

                this@SearchUseCase.maxPages = searchResult.totalResults / searchResult.itemsPerPage
                this@SearchUseCase.currentPage = searchResult.query.startPage

                if (newSearch) artists = mutableListOf()

                this@SearchUseCase.searchResult =
                    returnResults(searchResult.artistmatches.artists, newSearch)
            }
            is ResponseResult.Failure -> {
                this@SearchUseCase.searchResult = ResponseResult.Failure(searchModel.message)
            }
        }
    }

    private fun returnResults(
        newArtists: List<SearchArtist>,
        newSearch: Boolean
    ): ResponseResult<SearchCommon> {
        val result = if (newSearch) {
            artists = newArtists.toMutableList()
            SearchCommon(
                searchQuery,
                artists,
                newSearch
            )
        } else {
            artists.addAll(newArtists)

            SearchCommon(
                searchQuery,
                artists,
                newSearch,
                addedCount = newArtists.size
            )
        }
        return ResponseResult.Success(result)
    }

}
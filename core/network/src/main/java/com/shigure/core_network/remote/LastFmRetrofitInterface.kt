package com.shigure.core_network.remote

import com.shigure.core_network.model.albums.TopAlbumsResult
import com.shigure.core_network.model.details.AlbumInfoResult
import com.shigure.core_network.model.search.ArtistSearchResult
import retrofit2.http.GET
import retrofit2.http.Query

interface LastFmRetrofitInterface {

    @GET("/2.0/?method=artist.search&format=json")
    suspend fun searchForArtist(
        @Query("api_key") apiKey: String,
        @Query("artist") artist: String,
        @Query("page") page: Int?,
        @Query("limit ") limit: Int?
    ): ArtistSearchResult

    @GET("/2.0/?method=artist.gettopalbums&format=json")
    suspend fun getTopAlbums(
        @Query("api_key") apiKey: String,
        @Query("artist") artist: String?,
        @Query("mbid") mbid: String?,
        @Query("page") page: Int?,
        @Query("limit") limit: Int?
    ): TopAlbumsResult

    @GET("/2.0/?method=album.getinfo&format=json")
    suspend fun getAlbumInfo(
        @Query("api_key") apiKey: String,
        @Query("artist") artist: String?,
        @Query("album") album: String?,
        @Query("mbid") mbid: String?,
        @Query("lang") lang: String?,
        @Query("autocorrect") autoCorrect: Int?
    ): AlbumInfoResult
}
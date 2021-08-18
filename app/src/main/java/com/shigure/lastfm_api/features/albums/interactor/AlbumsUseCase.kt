package com.shigure.lastfm_api.features.albums.interactor

import com.shigure.core_network.model.getImageUrl
import com.shigure.core_network.remote.ResponseResult
import com.shigure.lastfm_api.features.albums.data.model.AlbumCommon
import com.shigure.lastfm_api.features.albums.data.model.AlbumsView
import com.shigure.lastfm_api.features.albums.repository.AlbumsRepository

class AlbumsUseCase(
    private val albumsRepository: AlbumsRepository,
) {

    private lateinit var artist: String

    private var albums: MutableList<AlbumCommon> = mutableListOf()
    private var currentPage: Int = 1
    private var totalPages: Int = 0

    private var albumsResult: ResponseResult<AlbumsView> = ResponseResult.Failure("")

    suspend fun init(artist: String): ResponseResult<AlbumsView> {
        this.artist = artist
        getArtistInfo(artist)
        return albumsResult
    }

    suspend fun loadMoreAlbums(): ResponseResult<AlbumsView> {
        if (currentPage >= totalPages) return ResponseResult.Empty()
        currentPage++
        getArtistInfo(artist)
        return albumsResult
    }

    suspend fun saveDeleteAlbum(position: Int): ResponseResult<AlbumsView> {
        val album = albums[position]
        try {
            val savedAlbum = if (album.isStored) {
                albumsRepository.deleteAlbum(album.artist.name, album.name)
                album.copy(isStored = false, isBeingProcessed = false)
            } else {
                albumsRepository.saveAlbum(album)
                album.copy(isStored = true, isBeingProcessed = false)
            }

            updateAlbum(savedAlbum)
        } catch (e: Exception) {
            val albumToReplace = album.copy(isStored = album.isStored, isBeingProcessed = false)
            updateAlbum(albumToReplace)

        }
        return albumsResult
    }

    private suspend fun getArtistInfo(artist: String) {

        val savedAlbums = albumsRepository.getSavedAlbums(artist).toMutableList()
        val topAlbumsResult = albumsRepository.getTopAlbums(artist, page = currentPage)
        val attributes = topAlbumsResult.topAlbums.attributes
        val albums = topAlbumsResult.topAlbums.albums.map { netAlbum ->
            val isStored =
                savedAlbums.removeAll { dbAlbum ->
                    dbAlbum.name == netAlbum.name && dbAlbum.artist == netAlbum.artist.name
                }

            AlbumCommon(
                netAlbum.name,
                netAlbum.playCount,
                netAlbum.url,
                netAlbum.artist,
                netAlbum.images.getImageUrl(),
                isStored = isStored
            )
        }

        this@AlbumsUseCase.currentPage = attributes.page
        this@AlbumsUseCase.totalPages = attributes.totalPages

        updateAlbums(albums)

    }

    private fun updateAlbums(newAlbums: List<AlbumCommon>) {
        val addedCount = if (currentPage > 1) {
            albums.addAll(newAlbums)
            newAlbums.size
        } else {
            albums = newAlbums.toMutableList()
            0
        }

        updateAlbumsState(addedCount)
    }

    private fun updateAlbum(savedAlbum: AlbumCommon) {
        albums = albums.map { if (it.name == savedAlbum.name) savedAlbum else it }.toMutableList()
        updateAlbumsState()
    }

    private fun updateAlbumsState(addedCount: Int = 0) {
        albumsResult = ResponseResult.Success(AlbumsView(artist, albums, addedCount = addedCount))
    }

}
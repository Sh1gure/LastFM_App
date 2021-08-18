package com.shigure.lastfm_api.features.main.interactor

import com.shigure.core_data_base.entity.AlbumDb
import com.shigure.lastfm_api.features.main.repository.AlbumsRepository
import kotlinx.coroutines.flow.Flow

class MainUseCase(
    private val albumsRepository: AlbumsRepository
) {

    suspend fun getAlbumsFlow(): Flow<List<AlbumDb>?> = albumsRepository.getAlbumsFlow()

}
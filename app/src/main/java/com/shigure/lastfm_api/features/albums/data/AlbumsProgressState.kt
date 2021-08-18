package com.shigure.lastfm_api.features.albums.data

import com.shigure.lastfm_api.features.albums.data.model.AlbumsView

sealed class AlbumsProgressState {

    object Loading: AlbumsProgressState()
    class Error(val error: String): AlbumsProgressState()
    class AlbumsReceived(val albums: AlbumsView): AlbumsProgressState()

}
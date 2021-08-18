package com.shigure.lastfm_api.features.albums.presentation

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shigure.lastfm_api.R
import com.shigure.core_network.remote.ResponseResult
import com.shigure.core_common.resourceProvider.ResourceProvider
import com.shigure.lastfm_api.features.albums.data.AlbumsProgressState
import com.shigure.lastfm_api.features.albums.data.model.AlbumsView
import com.shigure.lastfm_api.features.albums.interactor.AlbumsUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class AlbumsViewModel(
    private val useCase: AlbumsUseCase,
    private val resourceProvider: ResourceProvider
) : ViewModel(), LifecycleObserver {

    private val tag = "AlbumsViewModel"

    private val _albumsState = MutableLiveData<AlbumsProgressState>()
    val albumsState: MutableLiveData<AlbumsProgressState>
        get() = _albumsState

    fun init(artist: String) {
        viewModelScope.launch {
            try {
                albumsState.postValue(AlbumsProgressState.Loading)
                display(useCase.init(artist))
            } catch (error: Throwable) {
                processErrors(error)
            }
        }
    }

    fun loadMoreAlbums() {
        viewModelScope.launch {
            try {
                albumsState.postValue(AlbumsProgressState.Loading)
                display(useCase.loadMoreAlbums())
            } catch (error: Throwable) {
                processErrors(error)
            }
        }
    }

    fun saveDeleteAlbum(position: Int) {
        viewModelScope.launch {
            try {
                display(useCase.saveDeleteAlbum(position))
            } catch (error: Throwable) {
                processErrors(error)
            }
        }
    }

    private fun display(response: ResponseResult<AlbumsView>) {
        when (response) {
            is ResponseResult.Success -> {
                _albumsState.postValue(AlbumsProgressState.AlbumsReceived(response.data))
            }
            is ResponseResult.Failure -> {
                displayError(response.message)
            }
            is ResponseResult.Empty -> {
                Log.i(tag, "repetitive action canceled")
            }
        }

    }


    private fun processErrors(error: Throwable) {
        when (error) {
            is CancellationException -> {
                Log.e(tag, "coroutine job was canceled", error)
            }
            is Exception -> {
                Log.e(tag, "coroutine job was canceled", error)
                displayError(resourceProvider.getString(R.string.error_unknown) + error.localizedMessage)
            }

        }
    }

    private fun displayError(error: String) {
        _albumsState.postValue(AlbumsProgressState.Error(error))
    }

}
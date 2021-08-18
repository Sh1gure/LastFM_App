package com.shigure.lastfm_api.features.details.presentation

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shigure.lastfm_api.R
import com.shigure.core_network.remote.ResponseResult
import com.shigure.core_network.remote.util.NetworkChecker
import com.shigure.core_common.resourceProvider.ResourceProvider
import com.shigure.lastfm_api.features.details.data.DetailsProgressState
import com.shigure.lastfm_api.features.details.data.model.AlbumView
import com.shigure.lastfm_api.features.details.data.model.DetailsCommon
import com.shigure.lastfm_api.features.details.repository.DetailsRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val repo: DetailsRepository,
    private val networkChecker: NetworkChecker,
    private val resourceProvider: ResourceProvider
) : ViewModel(), LifecycleObserver {

    private val tag = "DetailsViewModel"

    private var job: Job? = null
    private val _detailsState: MutableLiveData<DetailsProgressState> = MutableLiveData()
    val detailsState: MutableLiveData<DetailsProgressState>
        get() = _detailsState


    fun getDetails(artist: String, album: String) {
        job = viewModelScope.launch {
            _detailsState.postValue(DetailsProgressState.Loading)
            try {
                val result = repo.getAlbumDetails(artist, album)
                display(result)
            } catch (error: Throwable) {
                processErrors(error)
            }
        }
    }

    private fun display(response: ResponseResult<AlbumView>) {
        when (response) {
            is ResponseResult.Success -> {
                _detailsState.postValue(DetailsProgressState.DisplayDescription(
                    DetailsCommon(
                        name = response.data.name,
                        artist = response.data.artist,
                        tracks = response.data.tracks,
                        imageUrl = response.data.imageUrl,
                        isOnline = networkChecker.isNetworkConnected
                    )
                ))
            }
            is ResponseResult.Failure -> {
                displayError(response.message)
            }
            is ResponseResult.Empty -> {
                Log.i(tag, "repetitive action canceled")
            }
        }

    }

    private fun displayError(error: String) {
        _detailsState.postValue(DetailsProgressState.Error(error))
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

}
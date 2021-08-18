package com.shigure.lastfm_api.features.search.presentation

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shigure.lastfm_api.R
import com.shigure.core_network.remote.ResponseResult
import com.shigure.core_common.resourceProvider.ResourceProvider
import com.shigure.lastfm_api.features.search.data.SearchProgressState
import com.shigure.lastfm_api.features.search.data.model.SearchCommon
import com.shigure.lastfm_api.features.search.interactor.SearchUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class SearchViewModel(
    private val useCase: SearchUseCase,
    private val resourceProvider: ResourceProvider
) : ViewModel(), LifecycleObserver {

    private val tag = "SearchViewModel"

    private val _searchState = MutableLiveData<SearchProgressState>()
    val searchState
        get() = _searchState

    fun loadMoreArtists() {
        viewModelScope.launch {
            try {
                val result = useCase.loadMoreArtists()
                display(result)
            } catch (error: Throwable) {
                processErrors(error)
            }
        }
    }

    fun performNewSearch(searchQuery: String) {
        viewModelScope.launch {
            try {
                searchState.postValue(SearchProgressState.Loading)
                val result = useCase.performNewSearch(searchQuery)
                display(result)
            } catch (error: Throwable) {
                processErrors(error)
            }
        }
    }

    private fun display(response: ResponseResult<SearchCommon>) {
        when (response) {
            is ResponseResult.Success -> {
                _searchState.postValue(SearchProgressState.Artists(response.data))
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
        _searchState.postValue(SearchProgressState.Error(error))
    }
}
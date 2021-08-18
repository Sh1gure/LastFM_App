package com.shigure.lastfm_api.features.main.presentation

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shigure.core_data_base.entity.AlbumDb
import com.shigure.lastfm_api.features.main.interactor.MainUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class MainViewModel(
    private val useCase: MainUseCase
) : ViewModel(), LifecycleObserver {

    private val _mainState: MutableLiveData<List<AlbumDb>> = MutableLiveData()
    val mainState: MutableLiveData<List<AlbumDb>>
        get() = _mainState

    init {
        viewModelScope.launch {
            useCase.getAlbumsFlow().collect {
                _mainState.postValue(it)
            }
        }
    }

}
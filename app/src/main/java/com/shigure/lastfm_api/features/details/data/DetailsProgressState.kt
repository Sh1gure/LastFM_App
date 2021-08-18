package com.shigure.lastfm_api.features.details.data

import com.shigure.lastfm_api.features.details.data.model.DetailsCommon

sealed class DetailsProgressState {

    object Loading: DetailsProgressState()
    class DisplayDescription(val details: DetailsCommon): DetailsProgressState()
    class Error(val error: String): DetailsProgressState()

}
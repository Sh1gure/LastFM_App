package com.shigure.lastfm_api.features.search.data

import com.shigure.lastfm_api.features.search.data.model.SearchCommon

sealed class SearchProgressState {

    object Loading: SearchProgressState()
    class Error(val error: String): SearchProgressState()
    class Artists(val artists: SearchCommon): SearchProgressState()

}

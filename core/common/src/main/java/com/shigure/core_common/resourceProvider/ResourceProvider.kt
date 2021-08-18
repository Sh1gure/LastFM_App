package com.shigure.core_common.resourceProvider

import androidx.annotation.StringRes

interface ResourceProvider {

    fun getString(@StringRes resourceIdentifier: Int, vararg arguments: Any): String

}
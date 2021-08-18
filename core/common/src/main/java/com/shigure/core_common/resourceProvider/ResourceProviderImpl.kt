package com.shigure.core_common.resourceProvider

import android.content.Context
import androidx.annotation.*

class ResourceProviderImpl(private val context: Context) : ResourceProvider {

    override fun getString(@StringRes resourceIdentifier: Int, vararg arguments: Any): String {
        return if (arguments.isNotEmpty()) {
            context.resources.getString(resourceIdentifier, *arguments)
        } else {
            context.resources.getString(resourceIdentifier)
        }
    }
}
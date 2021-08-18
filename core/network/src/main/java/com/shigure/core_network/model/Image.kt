package com.shigure.core_network.model

import com.google.gson.annotations.SerializedName

data class Image(
    val size: Size = Size.Medium,
    @SerializedName("#text") val url: String = ""
) {
    enum class Size {
        @SerializedName("medium")
        Medium
    }
}

fun List<Image>.getImageUrl(size: Image.Size = Image.Size.Medium): String =
    firstOrNull { it.size == size }?.url ?: ""
package com.shigure.lastfm_api.features.albums.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.shigure.lastfm_api.features.albums.data.model.AlbumCommon

object AlbumsDiffCallback : DiffUtil.ItemCallback<AlbumCommon>() {
    override fun areItemsTheSame(oldItem: AlbumCommon, newItem: AlbumCommon): Boolean =
        oldItem.url == newItem.url

    override fun areContentsTheSame(oldItem: AlbumCommon, newItem: AlbumCommon): Boolean =
        oldItem == newItem
}
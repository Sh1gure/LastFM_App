package com.shigure.lastfm_api.features.main.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.shigure.core_data_base.entity.AlbumDb

object AlbumsDiffCallback : DiffUtil.ItemCallback<AlbumDb>() {
    override fun areItemsTheSame(oldItem: AlbumDb, newItem: AlbumDb): Boolean =
        oldItem.artist == newItem.artist && oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: AlbumDb, newItem: AlbumDb): Boolean =
        oldItem == newItem
}
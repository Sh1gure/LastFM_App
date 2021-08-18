package com.shigure.lastfm_api.features.details.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.shigure.lastfm_api.features.details.data.model.TrackCommon

object DetailsDiffCallback : DiffUtil.ItemCallback<TrackCommon>() {
    override fun areItemsTheSame(oldItem: TrackCommon, newItem: TrackCommon): Boolean =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: TrackCommon, newItem: TrackCommon): Boolean =
        oldItem == newItem
}
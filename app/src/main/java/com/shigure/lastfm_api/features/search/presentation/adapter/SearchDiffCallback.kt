package com.shigure.lastfm_api.features.search.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.shigure.core_network.model.search.SearchArtist

object SearchDiffCallback : DiffUtil.ItemCallback<SearchArtist>() {
    override fun areItemsTheSame(oldItem: SearchArtist, newItem: SearchArtist): Boolean =
        oldItem.url == newItem.url

    override fun areContentsTheSame(oldItem: SearchArtist, newItem: SearchArtist): Boolean =
        oldItem == newItem
}
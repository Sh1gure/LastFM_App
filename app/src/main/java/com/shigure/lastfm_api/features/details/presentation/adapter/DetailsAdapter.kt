package com.shigure.lastfm_api.features.details.presentation.adapter

import com.shigure.lastfm_api.R
import com.shigure.lastfm_api.databinding.ItemTrackBinding
import com.shigure.lastfm_api.features.details.data.model.TrackCommon
import com.shigure.core_common.format.formatDurationTime
import com.shigure.core_common.widget.SimpleListAdapter


class DetailsAdapter : SimpleListAdapter<TrackCommon>(DetailsDiffCallback) {
    override val layoutRes: Int
        get() = R.layout.item_track

    override fun bind(position: Int, holder: ViewHolder) {

        val binding = ItemTrackBinding.bind(holder.containerView)

        val track = getItem(position)
        val duration = formatDurationTime(track.duration)

        binding.title.text = track.name
        binding.duration.text = duration
    }
}
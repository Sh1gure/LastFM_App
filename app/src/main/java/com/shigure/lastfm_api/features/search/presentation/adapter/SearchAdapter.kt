package com.shigure.lastfm_api.features.search.presentation.adapter

import com.shigure.lastfm_api.R
import com.shigure.core_network.model.getImageUrl
import com.shigure.core_network.model.search.SearchArtist
import com.shigure.core_common.format.formatBigNumber
import com.shigure.core_common.ui.GlideExtension
import com.shigure.core_common.widget.SimpleListAdapter
import com.shigure.lastfm_api.databinding.ItemArtistBinding

private typealias OnArtistClickListener = (position: Int) -> Unit

class SearchAdapter : SimpleListAdapter<SearchArtist>(SearchDiffCallback) {
    override val layoutRes: Int
        get() = R.layout.item_artist

    override val bottomReachLimit: Int
        get() = 5

    private var onArtistClickListener: OnArtistClickListener? = null

    fun onArtistClicked(listener: OnArtistClickListener) {
        onArtistClickListener = listener
    }

    override fun bind(position: Int, holder: ViewHolder) {

        val binding = ItemArtistBinding.bind(holder.containerView)

        val context = binding.root.context
        val artist = getItem(position)
        val listeners = formatBigNumber(artist.listeners)

        binding.name.text = artist.name
        binding.listeners.text = context.getString(R.string.listeners_count, listeners)

        GlideExtension.loadImage(context, binding.artistCover, artist.images.getImageUrl())

        binding.root.setOnClickListener {
            onArtistClickListener?.invoke(position)
        }
    }
}
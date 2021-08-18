package com.shigure.lastfm_api.features.main.presentation.adapter

import com.shigure.lastfm_api.R
import com.shigure.core_common.format.formatBigNumber
import com.shigure.core_common.ui.GlideExtension
import com.shigure.core_common.widget.SimpleListAdapter
import com.shigure.core_data_base.entity.AlbumDb
import com.shigure.lastfm_api.databinding.ItemSavedAlbumBinding

private typealias OnAlbumClickListener = (position: Int) -> Unit

class MainAdapter : SimpleListAdapter<AlbumDb>(AlbumsDiffCallback) {
    override val layoutRes: Int
        get() = R.layout.item_saved_album

    private var onAlbumClickListener: OnAlbumClickListener? = null

    fun onAlbumClicked(listener: OnAlbumClickListener) {
        onAlbumClickListener = listener
    }

    override fun bind(position: Int, holder: ViewHolder) {

        val binding = ItemSavedAlbumBinding.bind(holder.containerView)

        val context = binding.root.context
        val album = getItem(position)
        val playCount = formatBigNumber(album.playCount)

        binding.name.text = album.name
        binding.playCount.text = context.getString(R.string.play_count, playCount)

        GlideExtension.loadImage(context, binding.albumCover, album.imageUrl)

        binding.root.setOnClickListener {
            onAlbumClickListener?.invoke(position)
        }
    }
}
package com.shigure.lastfm_api.features.albums.presentation.adapter

import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.shigure.lastfm_api.R
import com.shigure.core_common.format.formatBigNumber
import com.shigure.core_common.ui.GlideExtension
import com.shigure.core_common.widget.SimpleListAdapter
import com.shigure.lastfm_api.databinding.ItemAlbumBinding
import com.shigure.lastfm_api.features.albums.data.model.AlbumCommon


private typealias OnAlbumClickListener = (position: Int) -> Unit
private typealias OnSaveDeleteClickListener = (position: Int) -> Unit

class AlbumsAdapter : SimpleListAdapter<AlbumCommon>(AlbumsDiffCallback) {
    override val layoutRes: Int
        get() = R.layout.item_album

    override val bottomReachLimit: Int
        get() = 1

    private var onAlbumClickListener: OnAlbumClickListener? = null
    private var onSaveDeleteClickListener: OnSaveDeleteClickListener? = null

    fun onAlbumClicked(listener: OnAlbumClickListener) {
        onAlbumClickListener = listener
    }

    fun onSaveDeleteClicked(listener: OnSaveDeleteClickListener) {
        onSaveDeleteClickListener = listener
    }

    override fun bind(position: Int, holder: ViewHolder) {

        val binding = ItemAlbumBinding.bind(holder.containerView)

        val context = binding.root.context
        val album = getItem(position)
        val playCount = formatBigNumber(album.playCount)

        binding.name.text = album.name
        binding.playCount.text = context.getString(R.string.play_count, playCount)

        GlideExtension.loadImage(
            context,
            binding.albumCover,
            album.imageUrl
        )

        holder.containerView.setOnClickListener {
            onAlbumClickListener?.invoke(position)
        }

        binding.progress.isVisible = album.isBeingProcessed
        binding.saveDeleteButton.isInvisible = album.isBeingProcessed
        binding.saveDeleteButton.isActivated = album.isStored

        binding.saveDeleteButton.setOnClickListener {
            album.isBeingProcessed = true
            binding.progress.isVisible = true
            binding.saveDeleteButton.isInvisible = true
            onSaveDeleteClickListener?.invoke(position)
        }
    }
}
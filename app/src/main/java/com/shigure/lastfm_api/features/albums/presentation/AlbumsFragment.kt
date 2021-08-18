package com.shigure.lastfm_api.features.albums.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.shigure.lastfm_api.R
import com.shigure.core_common.messages.showInfoMessage
import com.shigure.core_common.ui.viewBinding
import com.shigure.lastfm_api.databinding.FragmentAlbumsBinding
import com.shigure.lastfm_api.features.albums.data.AlbumsProgressState
import com.shigure.lastfm_api.features.albums.presentation.adapter.AlbumsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlbumsFragment :
    Fragment(R.layout.fragment_albums) {

    private val binding by viewBinding(FragmentAlbumsBinding::bind)
    private val albumsViewModel: AlbumsViewModel by viewModel()

    private lateinit var adapter: AlbumsAdapter
    private val args: AlbumsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModelObserving()
        setupRecycler()
        setupListeners()
        albumsViewModel.init(args.artist)
        requireActivity().onBackPressedDispatcher.addCallback(object :
            OnBackPressedCallback(requireActivity().onBackPressedDispatcher.hasEnabledCallbacks()) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })
    }

    private fun initViewModelObserving() {
        albumsViewModel.albumsState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is AlbumsProgressState.AlbumsReceived -> {
                    val albums = state.albums

                    binding.artistName.text = albums.artist
                    adapter.submitList(albums.albums) {
                        if (albums.addedCount > 0) {
                            adapter.notifyItemRangeInserted(
                                adapter.currentList.size,
                                albums.addedCount
                            )
                        }
                        binding.placeholder.isVisible = adapter.currentList.isEmpty()
                    }
                    adapter.onBottomReached(albumsViewModel::loadMoreAlbums)
                    setLoadingEnabled(false)
                }
                is AlbumsProgressState.Loading -> {
                    setLoadingEnabled(true)
                }
                is AlbumsProgressState.Error -> {
                    setLoadingEnabled(false)
                    showInfoMessage(requireContext(), state.error)
                }
            }
        }
    }

    private fun setupRecycler() {
        adapter = AlbumsAdapter()

        adapter.onSaveDeleteClicked(albumsViewModel::saveDeleteAlbum)
        adapter.onAlbumClicked {
            val album = adapter.currentList[it]
            navigateToDetails(album = album.name, artist = album.artist.name)
        }
        binding.recycler.layoutManager = LinearLayoutManager(context)
        binding.recycler.adapter = adapter
    }

    private fun setLoadingEnabled(enabled: Boolean) {
        binding.progressBar.isVisible = enabled

        adapter.onBottomReached(if (enabled) null else albumsViewModel::loadMoreAlbums)
    }

    private fun setupListeners() {
        binding.homeButton.setOnClickListener {
            navigateToHome()
        }
    }

    private fun navigateToDetails(artist: String, album: String) {
        val action = AlbumsFragmentDirections.actionAlbumsFragmentToDetailsFragment(artist, album)
        findNavController().navigate(action)
    }

    private fun navigateToHome() {
        val action = AlbumsFragmentDirections.actionAlbumsFragmentToMainFragment()
        findNavController().navigate(action)
    }

}
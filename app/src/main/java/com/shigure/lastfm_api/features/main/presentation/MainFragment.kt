package com.shigure.lastfm_api.features.main.presentation

import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.shigure.lastfm_api.R
import com.shigure.core_common.ui.viewBinding
import com.shigure.lastfm_api.databinding.FragmentMainBinding
import com.shigure.lastfm_api.features.main.presentation.adapter.MainAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var adapter: MainAdapter
    private val binding by viewBinding(FragmentMainBinding::bind)
    private val mainViewModel: MainViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModelObserving()
        setupRecycler()
        binding.searchButton.setOnClickListener {
            navigateToSearch()
        }
        requireActivity().onBackPressedDispatcher.addCallback(object :
            OnBackPressedCallback(requireActivity().onBackPressedDispatcher.hasEnabledCallbacks()) {
            override fun handleOnBackPressed() {

            }
        })
    }

    private fun initViewModelObserving() {
        mainViewModel.mainState.observe(viewLifecycleOwner) { albums ->
            adapter.submitList(albums) {
                binding.placeholder.isVisible = adapter.currentList.isEmpty()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mainViewModel
    }

    private fun setupRecycler() {
        adapter = MainAdapter()

        adapter.onAlbumClicked {
            val album = adapter.currentList[it]
            navigateToDetails(album = album.name, artist = album.artist)
        }
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun navigateToDetails(artist: String, album: String) {
        val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(artist, album)
        findNavController().navigate(action)
    }

    private fun navigateToSearch() {
        val action = MainFragmentDirections.actionMainFragmentToSearchFragment()
        findNavController().navigate(action)
    }

}
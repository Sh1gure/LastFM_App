package com.shigure.lastfm_api.features.search.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.shigure.lastfm_api.R
import com.shigure.core_common.messages.showInfoMessage
import com.shigure.core_common.ui.viewBinding
import com.shigure.lastfm_api.databinding.FragmentSearchBinding
import com.shigure.lastfm_api.features.search.data.SearchProgressState
import com.shigure.lastfm_api.features.search.presentation.adapter.SearchAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment :
    Fragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val TAG = "SearchFragment"
    private val searchViewModel: SearchViewModel by viewModel()

    private lateinit var adapter: SearchAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModelObserving()
        setupListeners()
        setupRecycler()
        requireActivity().onBackPressedDispatcher.addCallback(object :
            OnBackPressedCallback(requireActivity().onBackPressedDispatcher.hasEnabledCallbacks()) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })
    }

    private fun initViewModelObserving() {
        searchViewModel.searchState.observe(viewLifecycleOwner) { state ->

            when (state) {
                is SearchProgressState.Artists -> {
                    setLoadingEnabled(false)

                    val artists = state.artists

                    binding.searchField.setText(artists.searchQuery)
                    binding.searchField.setSelection(artists.searchQuery.length)

                    adapter.submitList(artists.artists) {
                        if (!artists.newSearch) {
                            adapter.notifyItemRangeInserted(
                                adapter.currentList.size,
                                artists.addedCount
                            )
                        }
                        binding.placeholder.isVisible = adapter.currentList.isEmpty()
                    }
                }
                is SearchProgressState.Loading -> {
                    setLoadingEnabled(true)
                }
                is SearchProgressState.Error -> {
                    setLoadingEnabled(false)
                    showInfoMessage(requireContext(), state.error)
                }
            }

        }
    }

    private fun setupListeners() {
        binding.searchButton.setOnClickListener {
            hideKeyboard()
            performNewSearch()
        }
        binding.homeButton.setOnClickListener {
            navigateToMain()
        }
    }

    private fun performNewSearch() {
        binding.recycler.scrollToPosition(0)
        searchViewModel.performNewSearch(
            binding.searchField.text.toString()
        )
    }

    private fun setupRecycler() {
        adapter = SearchAdapter()

        adapter.onArtistClicked {
            navigateToAlbums(adapter.currentList[it].name)
        }
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter
    }

    private fun setLoadingEnabled(enabled: Boolean) {
        binding.progressBar.isVisible = enabled
        binding.searchButton.isInvisible = enabled

        adapter.onBottomReached(if (enabled) null else searchViewModel::loadMoreArtists)
    }

    private fun hideKeyboard() {
        try {
            val inputMethodManager =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0)
        } catch (e: Exception) {
            Log.e(TAG, "hide keyboard", e)
        }
    }

    private fun navigateToAlbums(artist: String) {
        val action = SearchFragmentDirections.actionSearchFragmentToAlbumsFragment(artist)
        findNavController().navigate(action)
    }

    private fun navigateToMain() {
        val action = SearchFragmentDirections.actionSearchFragmentToMainFragment()
        findNavController().navigate(action)
    }

}
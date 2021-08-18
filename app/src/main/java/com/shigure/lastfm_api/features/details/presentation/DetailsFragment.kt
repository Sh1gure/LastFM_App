package com.shigure.lastfm_api.features.details.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.shigure.lastfm_api.R
import com.shigure.lastfm_api.features.details.presentation.adapter.DetailsAdapter
import com.shigure.core_common.messages.showInfoMessage
import com.shigure.core_common.ui.GlideExtension
import com.shigure.core_common.ui.viewBinding
import com.shigure.lastfm_api.databinding.FragmentDetailsBinding
import com.shigure.lastfm_api.features.details.data.DetailsProgressState
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment :
    Fragment(R.layout.fragment_details) {

    private val binding by viewBinding(FragmentDetailsBinding::bind)
    private lateinit var adapter: DetailsAdapter
    private val detailsViewModel: DetailsViewModel by viewModel()
    private val args: DetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModelObserving()
        setupRecycler()
        requireActivity().onBackPressedDispatcher.addCallback(object :
            OnBackPressedCallback(requireActivity().onBackPressedDispatcher.hasEnabledCallbacks()) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        detailsViewModel.getDetails(args.artist, args.album)
    }

    private fun setupRecycler() {
        adapter = DetailsAdapter()
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initViewModelObserving() {
        detailsViewModel.detailsState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is DetailsProgressState.DisplayDescription -> {
                    adapter.submitList(state.details.tracks)
                    GlideExtension.loadImage(
                        requireContext(),
                        binding.albumCover,
                        state.details.imageUrl
                    )
                    binding.placeholder.isVisible = adapter.currentList.isEmpty()

                    binding.albumName.text = state.details.name
                    binding.artistName.text = state.details.artist

                    binding.progressBar.isVisible = false
                }
                is DetailsProgressState.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is DetailsProgressState.Error -> {
                    showInfoMessage(requireContext(), state.error)
                    binding.progressBar.isVisible = true
                }
            }
        }
    }

    private fun setupListeners() {
        binding.homeButton.setOnClickListener {
            navigationHome()
        }
    }

    private fun navigationHome() {
        val action = DetailsFragmentDirections.actionDetailsFragmentToMainFragment()
        findNavController().navigate(action)
    }


}
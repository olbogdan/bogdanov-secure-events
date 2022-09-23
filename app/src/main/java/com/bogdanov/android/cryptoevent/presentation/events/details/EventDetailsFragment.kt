package com.bogdanov.android.cryptoevent.presentation.events.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bogdanov.android.cryptoevent.R
import com.bogdanov.android.cryptoevent.databinding.FragmentEventDetailsBinding
import com.bogdanov.android.cryptoevent.presentation.UIState
import com.bogdanov.android.cryptoevent.presentation.extensions.toReadable
import com.bogdanov.domain.interactors.EventEntity
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.io.File


@AndroidEntryPoint
class EventDetailsFragment : Fragment() {

    private var _binding: FragmentEventDetailsBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("invalid binding state")
    private val viewModel: EventDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarContainer.toolbar.setNavigationOnClickListener {
            view.findNavController().navigateUp()
        }
        viewModel.uiState.observe(viewLifecycleOwner) { updateUI(it) }
    }

    private fun updateUI(uiState: UIState<EventEntity>) {
        when (uiState) {
            is UIState.Loading -> {}
            is UIState.Success -> {
                val event = uiState.data
                binding.eventDetailsTextTitle.text = event.title
                binding.eventDetailsTextSubtitle.text = event.description
                binding.eventDetailsContainer.eventDetailsTextCreationDate.text =
                    event.creationDate.toReadable()
                binding.eventDetailsContainer.eventDetailsTextDueDate.text = event.dueDate.toReadable()
                binding.toolbarContainer.toolbar.title =
                    getString(R.string.event_details_toolbar_title, event.id)
                binding.eventDetailsBtnDownload.setOnClickListener {
                    onDownloadImage(event)
                }
                binding.eventDetailsBtnDownload.isVisible = event.image.isNullOrEmpty()
                event.image?.let { image ->
                    Picasso.get()
                        .load(File(image))
                        .centerCrop()
                        .fit()
                        .into(binding.eventDetailsImage)
                }
            }
            is UIState.Failure -> {
                Toast.makeText(requireContext(), uiState.errorMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun onDownloadImage(event: EventEntity) {
        viewModel.downloadImageForEvent(event.id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
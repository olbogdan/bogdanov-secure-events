package com.bogdanov.android.cryptoevent.presentation.events.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bogdanov.android.cryptoevent.databinding.FragmentEventsListBinding
import com.bogdanov.android.cryptoevent.presentation.UIState
import com.bogdanov.android.cryptoevent.presentation.events.EventFilter
import com.bogdanov.android.cryptoevent.presentation.home.HomeFragmentDirections
import com.bogdanov.domain.interactors.EventEntity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EventsListFragment : Fragment() {

    companion object {
        private const val BUNDLE_FILTER = "filter"
        fun newInstance(filter: EventFilter) =
            EventsListFragment().apply {
                arguments = bundleOf(BUNDLE_FILTER to filter.value)
            }
    }

    @Inject
    lateinit var assistedFactory: EventsListViewModel.VMFactory
    private var eventsAdapter: EventsListAdapter? = null
    private var _binding: FragmentEventsListBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("invalid binding state")
    private val viewModel: EventsListViewModel by viewModels {
        EventsListViewModel.Factory(assistedFactory, getEventFilter())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventsListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val lLayoutManager = LinearLayoutManager(context)
        eventsAdapter = EventsListAdapter { openEventDetails(it) }
        binding.eventsListRecyclerView.apply {
            adapter = eventsAdapter
            layoutManager = lLayoutManager
            addItemDecoration(DividerItemDecoration(requireContext(), lLayoutManager.orientation))
        }

        viewModel.uiState.observe(viewLifecycleOwner) { updateUI(it) }
    }

    private fun updateUI(uiState: UIState<List<EventEntity>>) {
        when(uiState) {
            is UIState.Loading -> {
                showProgress(true)
            }
            is UIState.Success -> {
                eventsAdapter?.submitList(uiState.data)
                showProgress(false)
            }
            is UIState.Failure -> {
                Toast.makeText(requireContext(), uiState.errorMessage, Toast.LENGTH_LONG).show()
                showProgress(false)
            }
        }
    }

    private fun showProgress(show: Boolean) {
        binding.eventsListProgressbar.isVisible = show
    }

    //note: we can handle navigation via ViewModel for nav testability
    private fun openEventDetails(event: EventEntity) {
        HomeFragmentDirections.actionHomeFragmentToEventDetailsFragment(event).let {
            findNavController().navigate(it)
        }
    }

    private fun getEventFilter(): EventFilter {
        val filterId = requireArguments().getInt(BUNDLE_FILTER)
        return EventFilter.getByValue(filterId)
            ?: throw IllegalArgumentException("Invalid filter id $filterId")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
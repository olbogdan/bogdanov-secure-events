package ch.protonmail.android.protonmailtest.presentation.taskslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ch.protonmail.android.protonmailtest.R
import ch.protonmail.android.protonmailtest.data.Task
import ch.protonmail.android.protonmailtest.databinding.FragmentTasksListBinding
import ch.protonmail.android.protonmailtest.presentation.home.HomeFragmentDirections
import ch.protonmail.android.protonmailtest.presentation.TaskFilter
import ch.protonmail.android.protonmailtest.presentation.UIState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TasksListFragment : Fragment() {
    companion object {
        private const val BUNDLE_FILTER = "filter"
        fun newInstance(filter: TaskFilter) =
            TasksListFragment().apply {
                arguments = bundleOf(BUNDLE_FILTER to filter.value)
            }
    }

    @Inject
    lateinit var assistedFactory: TasksListViewModel.VMFactory

    private var tasksAdapter: TasksAdapter? = null
    private var _binding: FragmentTasksListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TasksListViewModel by viewModels {
        TasksListViewModel.Factory(assistedFactory, getTaskFilter())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTasksListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tasksAdapter = TasksAdapter { openTaskDetails(it) }
        binding.tasksListRecyclerView.apply {
            adapter = tasksAdapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.uiState.observe(viewLifecycleOwner) { updateUI(it) }
    }

    private fun updateUI(uiState: UIState<List<Task>>) {
        when(uiState) {
            is UIState.Success -> {
                tasksAdapter?.submitList(uiState.data)
                binding.tasksListProgressbar.hide()
            }
            is UIState.Loading -> binding.tasksListProgressbar.show()
            is UIState.Failure -> {
                Toast.makeText(requireContext(), uiState.errorMessage, Toast.LENGTH_LONG).show()
                binding.tasksListProgressbar.hide()
            }
        }
    }

    //todo: handle navigation via ViewModel
    private fun openTaskDetails(task: Task) {
        HomeFragmentDirections.actionHomeFragmentToTaskDetailsFragment(task).let {
            findNavController().navigate(it)
        }
    }

    private fun getTaskFilter(): TaskFilter {
        val filterId = requireArguments().getInt(BUNDLE_FILTER)
        return TaskFilter.getByValue(filterId)
            ?: throw IllegalArgumentException("Invalid filter id $filterId")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
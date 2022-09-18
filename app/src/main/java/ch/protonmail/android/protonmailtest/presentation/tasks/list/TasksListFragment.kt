package ch.protonmail.android.protonmailtest.presentation.tasks.list

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
import ch.protonmail.android.protonmailtest.databinding.FragmentTasksListBinding
import ch.protonmail.android.protonmailtest.presentation.tasks.TaskFilter
import ch.protonmail.android.protonmailtest.presentation.tasks.TaskUIEntity
import ch.protonmail.android.protonmailtest.presentation.UIState
import ch.protonmail.android.protonmailtest.presentation.home.HomeFragmentDirections
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
    private val binding get() = _binding ?: throw IllegalStateException("invalid binding state")
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
        val lLayoutManager = LinearLayoutManager(context)
        tasksAdapter = TasksAdapter { openTaskDetails(it) }
        binding.tasksListRecyclerView.apply {
            adapter = tasksAdapter
            layoutManager = lLayoutManager
            addItemDecoration(DividerItemDecoration(requireContext(), lLayoutManager.orientation))
        }

        viewModel.uiState.observe(viewLifecycleOwner) { updateUI(it) }
    }

    private fun updateUI(uiState: UIState<List<TaskUIEntity>>) {
        when(uiState) {
            is UIState.Loading -> {
                showProgress(true)
            }
            is UIState.Success -> {
                tasksAdapter?.submitList(uiState.data)
                showProgress(false)
            }
            is UIState.Failure -> {
                Toast.makeText(requireContext(), uiState.errorMessage, Toast.LENGTH_LONG).show()
                showProgress(false)
            }
        }
    }

    private fun showProgress(show: Boolean) {
        binding.tasksListProgressbar.isVisible = show
    }

    //todo: handle navigation via ViewModel
    private fun openTaskDetails(task: TaskUIEntity) {
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
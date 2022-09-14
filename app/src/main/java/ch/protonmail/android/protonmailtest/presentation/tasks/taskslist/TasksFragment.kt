package ch.protonmail.android.protonmailtest.presentation.tasks.taskslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ch.protonmail.android.protonmailtest.R
import ch.protonmail.android.protonmailtest.data.Task
import ch.protonmail.android.protonmailtest.databinding.FragmentTasksListBinding
import ch.protonmail.android.protonmailtest.presentation.home.HomeFragmentDirections
import ch.protonmail.android.protonmailtest.presentation.tasks.TaskFilter

class TasksFragment : Fragment() {
    companion object {
        private const val BUNDLE_FILTER = "filter"
        fun newInstance(filter: TaskFilter) =
            TasksFragment().apply {
                arguments = bundleOf(BUNDLE_FILTER to filter.value)
            }
    }

    private var _binding: FragmentTasksListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TasksViewModel by navGraphViewModels(R.id.tasks_nav_graph) {
        TasksViewModel.TasksViewModelFactory(getTaskFilter())
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
        val taskAdapter = TasksAdapter { openTaskDetails(it) }
        binding.tasksListRecyclerView.apply {
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.tasks.observe(viewLifecycleOwner) { taskAdapter.submitList(it) }
        viewModel.fetchTasks(requireActivity() as AppCompatActivity) //todo: remove and use Repository pattern with coroutines in VM
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
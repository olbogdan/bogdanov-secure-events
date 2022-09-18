package ch.protonmail.android.protonmailtest.presentation.tasks.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import ch.protonmail.android.protonmailtest.R
import ch.protonmail.android.protonmailtest.databinding.FragmentTaskDetailsBinding
import ch.protonmail.android.protonmailtest.presentation.UIState
import ch.protonmail.android.protonmailtest.presentation.extensions.toReadable
import ch.protonmail.domain.interactors.TaskEntity
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.io.File


@AndroidEntryPoint
class TaskDetailsFragment : Fragment() {

    private var _binding: FragmentTaskDetailsBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("invalid binding state")
    private val viewModel: TaskDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarContainer.toolbar.setNavigationOnClickListener {
            view.findNavController().navigateUp()
        }
        viewModel.uiState.observe(viewLifecycleOwner) { updateUI(it) }
    }

    private fun updateUI(uiState: UIState<TaskEntity>) {
        when (uiState) {
            is UIState.Loading -> {}
            is UIState.Success -> {
                val task = uiState.data
                binding.taskDetailsTextTitle.text = task.title
                binding.taskDetailsTextSubtitle.text = task.description
                binding.taskDetailsContainer.taskDetailsTextCreationDate.text =
                    task.creationDate.toReadable()
                binding.taskDetailsContainer.taskDetailsTextDueDate.text = task.dueDate.toReadable()
                binding.toolbarContainer.toolbar.title =
                    getString(R.string.task_details_toolbar_title, task.id)
                binding.taskDetailsBtnDownload.setOnClickListener {
                    onDownloadImage(task)
                }
                binding.taskDetailsBtnDownload.isVisible = task.image.isNullOrEmpty()
                task.image?.let { image ->
                    Picasso.get()
                        .load(File(image))
                        .centerCrop()
                        .fit()
                        .into(binding.taskDetailsImage)
                }
            }
            is UIState.Failure -> {
                Toast.makeText(requireContext(), uiState.errorMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun onDownloadImage(task: TaskEntity) {
        viewModel.downloadImageForTask(task.id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
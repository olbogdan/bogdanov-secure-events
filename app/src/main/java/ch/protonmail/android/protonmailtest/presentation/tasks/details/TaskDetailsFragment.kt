package ch.protonmail.android.protonmailtest.presentation.tasks.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import ch.protonmail.android.protonmailtest.R
import ch.protonmail.android.protonmailtest.databinding.FragmentTaskDetailsBinding
import ch.protonmail.android.protonmailtest.presentation.extensions.toReadable


class TaskDetailsFragment : Fragment() {

    private val args: TaskDetailsFragmentArgs by navArgs()
    private var _binding: FragmentTaskDetailsBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("invalid binding state")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.taskDetailsBtnDownload.setOnClickListener {
            Log.d("DetailActivity", "Downloading the image...")
        }
        val task = args.task
        binding.taskDetailsTextTitle.text = task.title
        binding.taskDetailsTextSubtitle.text = task.description
        binding.taskDetailsContainer.taskDetailsTextCreationDate.text =
            task.creationDate.toReadable()
        binding.taskDetailsContainer.taskDetailsTextDueDate.text = task.dueDate.toReadable()

        binding.toolbarContainer.toolbar.title =
            getString(R.string.task_details_toolbar_title, task.id)
        binding.toolbarContainer.toolbar.setNavigationOnClickListener {
            view.findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
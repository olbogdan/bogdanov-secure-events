package ch.protonmail.android.protonmailtest.presentation.taskdetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import ch.protonmail.android.protonmailtest.databinding.FragmentTaskDetailsBinding

class TaskDetailsFragment : Fragment() {

    private val args: TaskDetailsFragmentArgs by navArgs()
    private var _binding: FragmentTaskDetailsBinding? = null
    private val binding get() = _binding!!

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
        binding.taskDetailsTextTitle.text = task.encryptedTitle
        binding.taskDetailsTextSubtitle.text = task.encryptedDescription
        binding.taskDetailsContainer.taskDetailsTextCreationDate.text = task.creationDate
        binding.taskDetailsContainer.taskDetailsTextDueDate.text = task.dueDate
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
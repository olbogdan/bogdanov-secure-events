package ch.protonmail.android.protonmailtest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ch.protonmail.android.protonmailtest.databinding.FragmentUpcomingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpcomingFragment : Fragment() {

    private val binding by lazy { FragmentUpcomingBinding.inflate(layoutInflater) }
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val layoutManager = LinearLayoutManager(context)
        val adapter = TasksAdapter()
        val recycler = binding.recyclerView
        recycler.adapter = adapter
        recycler.layoutManager = layoutManager
        viewModel.tasks.observe(viewLifecycleOwner) {
            adapter.submitData(it)
        }

        return binding.root
    }
}
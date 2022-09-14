package ch.protonmail.android.protonmailtest.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import ch.protonmail.android.protonmailtest.R
import ch.protonmail.android.protonmailtest.databinding.FragmentHomeBinding
import ch.protonmail.android.protonmailtest.presentation.tasks.TaskFilter
import ch.protonmail.android.protonmailtest.presentation.tasks.TasksPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : Fragment() {

    companion object {
        private var selectedTabPosition = TaskFilter.ALL_TASKS.value
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarContainer.title.text = getString(R.string.app_name)
        setupViewPager()
    }

    private fun setupViewPager() {
        val vpAdapter = TasksPagerAdapter(this)
        binding.homeViewPager.apply {
            adapter = vpAdapter
            currentItem = selectedTabPosition
            registerOnPageChangeCallback(
                object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        selectedTabPosition = position
                    }
                }
            )
        }

        // todo: update tasks count according to list size
        TabLayoutMediator(binding.homeTabLayout, binding.homeViewPager) { tab, position ->
            tab.text = when (position) {
                TaskFilter.ALL_TASKS.value -> getString(R.string.tab_all_tasks_title, 12)
                TaskFilter.UPCOMING_TASKS.value -> getString(R.string.tab_upcoming_tasks_title, 5)
                else -> throw IllegalStateException("Invalid TabLayoutMediator position $position")
            }
        }.attach()
    }
}
package com.bogdanov.android.cryptoevent.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.bogdanov.android.cryptoevent.presentation.events.EventFilter
import com.bogdanov.android.cryptoevent.presentation.events.EventsPagerAdapter
import com.bogdanov.android.cryptoevent.R
import com.bogdanov.android.cryptoevent.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    companion object {
        private var selectedTabPosition = EventFilter.ALL_EVENTS.value
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("invalid binding state")

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
        binding.toolbarContainer.toolbar.title = getString(R.string.app_name)
        binding.toolbarContainer.toolbar.navigationIcon = null
        setupViewPager()

    }

    private fun setupViewPager() {
        val vpAdapter = EventsPagerAdapter(this)
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

        // todo: update events count according to list size
        TabLayoutMediator(binding.homeTabLayout, binding.homeViewPager) { tab, position ->
            tab.text = when (position) {
                EventFilter.ALL_EVENTS.value -> getString(R.string.home_tab_all_events_title, 12)
                EventFilter.UPCOMING_EVENTS.value -> getString(R.string.home_tab_upcoming_events_title, 5)
                else -> throw IllegalStateException("Invalid TabLayoutMediator position $position")
            }
        }.attach()
    }
}
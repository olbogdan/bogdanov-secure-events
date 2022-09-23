package com.bogdanov.android.cryptoevent.presentation.events

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bogdanov.android.cryptoevent.presentation.events.list.EventsListFragment

class EventsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = EventFilter.values().size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            EventFilter.ALL_EVENTS.value -> EventsListFragment.newInstance(EventFilter.ALL_EVENTS)
            EventFilter.UPCOMING_EVENTS.value -> EventsListFragment.newInstance(EventFilter.UPCOMING_EVENTS)
            else -> throw IllegalStateException("Invalid adapter position $position max items: $itemCount")
        }
    }
}


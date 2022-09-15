package ch.protonmail.android.protonmailtest.presentation.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ch.protonmail.android.protonmailtest.presentation.TaskFilter
import ch.protonmail.android.protonmailtest.presentation.taskslist.TasksListFragment

class TasksPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = TaskFilter.values().size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            TaskFilter.ALL_TASKS.value -> TasksListFragment.newInstance(TaskFilter.ALL_TASKS)
            TaskFilter.UPCOMING_TASKS.value -> TasksListFragment.newInstance(TaskFilter.UPCOMING_TASKS)
            else -> throw IllegalStateException("Invalid adapter position $position max items: $itemCount")
        }
    }
}
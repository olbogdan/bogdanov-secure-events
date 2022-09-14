package ch.protonmail.android.protonmailtest.presentation.tasks

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ch.protonmail.android.protonmailtest.presentation.tasks.taskslist.TasksFragment

class TasksPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = TaskFilter.values().size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            TaskFilter.ALL_TASKS.value -> TasksFragment.newInstance(TaskFilter.ALL_TASKS)
            TaskFilter.UPCOMING_TASKS.value -> TasksFragment.newInstance(TaskFilter.UPCOMING_TASKS)
            else -> throw IllegalStateException("Invalid adapter position $position max items: $itemCount")
        }
    }
}
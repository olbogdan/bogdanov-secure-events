package ch.protonmail.android.protonmailtest.presentation.tasks.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ch.protonmail.android.protonmailtest.databinding.ItemTaskBinding
import ch.protonmail.android.protonmailtest.presentation.extensions.toReadable
import ch.protonmail.android.protonmailtest.presentation.tasks.TaskUIEntity

class TasksAdapter(private val clickListener: (TaskUIEntity) -> Unit) :
    ListAdapter<TaskUIEntity, TasksAdapter.ViewHolder>(ListAdapterCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            itemTaskTextTitle.text = item.title
            itemTaskTextDescription.text = item.description
            itemTaskDetailsContainer.taskDetailsTextCreationDate.text =
                item.creationDate.toReadable()
            itemTaskDetailsContainer.taskDetailsTextDueDate.text = item.dueDate.toReadable()
        }
        holder.binding.root.setOnClickListener { clickListener(item) }
    }

    class ViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)
}

private class ListAdapterCallBack : DiffUtil.ItemCallback<TaskUIEntity>() {
    override fun areItemsTheSame(oldItem: TaskUIEntity, newItem: TaskUIEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TaskUIEntity, newItem: TaskUIEntity): Boolean {
        return oldItem == newItem
    }
}
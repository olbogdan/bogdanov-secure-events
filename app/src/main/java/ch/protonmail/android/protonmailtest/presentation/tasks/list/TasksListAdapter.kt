package ch.protonmail.android.protonmailtest.presentation.tasks.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ch.protonmail.android.protonmailtest.databinding.ItemTaskBinding
import ch.protonmail.android.protonmailtest.presentation.extensions.toReadable
import ch.protonmail.domain.interactors.TaskEntity

class TasksListAdapter(private val clickListener: (TaskEntity) -> Unit) :
    ListAdapter<TaskEntity, TasksListAdapter.ViewHolder>(ListAdapterCallBack()) {

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

private class ListAdapterCallBack : DiffUtil.ItemCallback<TaskEntity>() {
    override fun areItemsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
        return oldItem == newItem
    }
}
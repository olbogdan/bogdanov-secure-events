package ch.protonmail.android.protonmailtest.presentation.tasks.taskslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ch.protonmail.android.crypto.CryptoLib
import ch.protonmail.android.protonmailtest.data.Task
import ch.protonmail.android.protonmailtest.databinding.ItemTaskBinding

class TasksAdapter(private val clickListener: (Task) -> Unit) : ListAdapter<Task, TasksAdapter.ViewHolder>(ListAdapterCallBack()) {
    private val cr = CryptoLib()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            itemTaskTextTitle.text = cr.decrypt(item.encryptedTitle).getOrNull()
            itemTaskTextDescription.text = cr.decrypt(item.encryptedDescription).getOrNull()
            itemTaskDetailsContainer.taskDetailsTextCreationDate.text = item.creationDate
            itemTaskDetailsContainer.taskDetailsTextDueDate.text = item.dueDate
        }
        holder.binding.root.setOnClickListener { clickListener(item) }
    }

    class ViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)
}

private class ListAdapterCallBack : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
}
package ch.protonmail.android.protonmailtest

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ch.protonmail.android.protonmailtest.databinding.ItemTaskBinding

class TasksAdapter : RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {
    private var data: List<Task> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) = with(holder.binding) {
        data[position].let {
            title.text = it.encryptedTitle
            description.text = it.encryptedDescription
            creationDate.text = it.creationDate
            dueDate.text = it.dueDate
        }
    }

    override fun getItemCount(): Int {
        return data.count()
    }

    fun submitData(list: List<Task>) {
        data = list
        notifyDataSetChanged()
    }

    class TaskViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)
}
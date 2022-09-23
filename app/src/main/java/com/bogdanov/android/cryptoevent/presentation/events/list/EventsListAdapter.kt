package com.bogdanov.android.cryptoevent.presentation.events.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bogdanov.android.cryptoevent.R
import com.bogdanov.android.cryptoevent.databinding.ItemEventBinding
import com.bogdanov.android.cryptoevent.presentation.extensions.toReadable
import com.bogdanov.domain.interactors.EventEntity
import com.squareup.picasso.Picasso
import java.io.File

class EventsListAdapter(private val clickListener: (EventEntity) -> Unit) :
    ListAdapter<EventEntity, EventsListAdapter.ViewHolder>(ListAdapterCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            itemEventTextTitle.text =
                holder.itemView.resources.getString(R.string.item_event_title, item.title, item.id)
            itemEventTextDescription.text = item.description
            itemEventDetailsContainer.eventDetailsTextCreationDate.text =
                item.creationDate.toReadable()
            itemEventDetailsContainer.eventDetailsTextDueDate.text = item.dueDate.toReadable()

            val image = item.image
            if (image != null) {
                Picasso.get()
                    .load(File(image))
                    .centerCrop()
                    .fit()
                    .into(itemEventImage)
            } else {
                itemEventImage.setImageDrawable(null)
            }
        }
        holder.binding.root.setOnClickListener { clickListener(item) }
    }

    class ViewHolder(val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root)
}

private class ListAdapterCallBack : DiffUtil.ItemCallback<EventEntity>() {
    override fun areItemsTheSame(oldItem: EventEntity, newItem: EventEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: EventEntity, newItem: EventEntity): Boolean {
        return oldItem == newItem
    }
}
package com.meuticket.pos.register.event.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meuticket.pos.register.event.presentation.EventRegisterViewModel
import com.meuticket.pos.shared.data.model.Event
import com.meuticket.pos.ui.components.ViewRegisterCell

class EventRegisterAdapter(val viewModel: EventRegisterViewModel): RecyclerView.Adapter<EventViewHolder>() {

    var localItems: MutableList<Event>

    init {
        localItems = viewModel.event.toMutableList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(ViewRegisterCell(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        })
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = localItems[position]
        holder.cellView.apply {
            title = event.name

            setEditClickListener {
                viewModel.editClicked(event)
            }

            setDeleteClickListener {
                viewModel.deleteClicked(event)
            }
        }
    }

    override fun getItemCount(): Int {
        return localItems.size
    }

    fun filter(text: String) {

        localItems = viewModel.event.filter { it.name.contains(text) }.toMutableList()
        notifyDataSetChanged()
    }
}

class EventViewHolder(val cellView: ViewRegisterCell): RecyclerView.ViewHolder(cellView)
package com.example.shanoop.database.Event.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shanoop.database.Event.EventDetailsActivity
import com.example.shanoop.database.Event.Modal.Event
import com.example.shanoop.database.R
import kotlinx.android.synthetic.main.event_item.view.*
import org.jetbrains.anko.intentFor

class EventAdapter(var events: ArrayList<Event>,var context:Context) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_item, parent, false)
        return ViewHolder(view,context)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(events.get(position))

    }


    inner class ViewHolder(itemView: View,context: Context) : RecyclerView.ViewHolder(itemView) {
        fun bind(e: Event) {
            itemView.event_name.text = e.name
            itemView.event_venue.text = e.venue
            itemView.event_date.text = e.date
            itemView.setOnClickListener {
                context.startActivity(context.intentFor<EventDetailsActivity>("event" to e))
            }


        }
    }
}
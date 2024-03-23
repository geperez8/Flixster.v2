package com.example.flixster

import actorObject
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flixsterv2.R
import movieItem

class actorAdapter (private val items: ArrayList<actorObject>) : RecyclerView.Adapter<actorAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val nameTextView: TextView
        val headshotImageView: ImageView

        init {
            nameTextView = itemView.findViewById(R.id.actorName)
            headshotImageView = itemView.findViewById(R.id.actorImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.actor_card, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        val item = items.get(position)
        // Set item views based on views and data model
        holder.nameTextView.text = item.name

        val imageUrl = "https://image.tmdb.org/t/p/w500/" + item.headshotUrl

        Glide.with(holder.itemView)
            .load(imageUrl)
            .centerInside()
            .into(holder.headshotImageView)
        //holder.urlTextView.text = item.
    }
}
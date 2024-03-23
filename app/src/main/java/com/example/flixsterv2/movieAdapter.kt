package com.example.flixster

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flixsterv2.R
import movieItem

class movieAdapter (private val items: ArrayList<movieItem>) : RecyclerView.Adapter<movieAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val nameTextView: TextView
        val descriptionTextView: TextView
        val posterImageView: ImageView

        init {
            nameTextView = itemView.findViewById(R.id.movieTitle)
            descriptionTextView = itemView.findViewById(R.id.movieDescription)
            posterImageView = itemView.findViewById(R.id.moviePoster)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.movie_item, parent, false)
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
        holder.descriptionTextView.text = item.description

        val imageUrl = "https://image.tmdb.org/t/p/w500/" + item.posterUrl

        Glide.with(holder.itemView)
            .load(imageUrl)
            .centerInside()
            .into(holder.posterImageView)
        //holder.urlTextView.text = item.
    }
}
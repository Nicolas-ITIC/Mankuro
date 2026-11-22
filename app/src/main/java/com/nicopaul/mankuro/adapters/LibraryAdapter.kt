package com.nicopaul.mankuro.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nicopaul.mankuro.R
import com.nicopaul.mankuro.models.LibraryItem

class LibraryAdapter(
    private val items: MutableList<LibraryItem>,
    private val onItemClick: (LibraryItem, Int) -> Unit,
    private val onItemLongClick: (LibraryItem, Int) -> Unit
) : RecyclerView.Adapter<LibraryAdapter.LibraryViewHolder>() {

    class LibraryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivCover: ImageView = itemView.findViewById(R.id.ivCover)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvType: TextView = itemView.findViewById(R.id.tvType)
        val tvProgress: TextView = itemView.findViewById(R.id.tvProgress)
        val tvRating: TextView = itemView.findViewById(R.id.tvRating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.library_items, parent, false)
        return LibraryViewHolder(view)
    }

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        val item = items[position]
        holder.tvTitle.text = item.title
        holder.tvType.text = item.type
        holder.tvProgress.text = item.progress
        holder.tvRating.text = item.rating
        holder.ivCover.setImageResource(item.imageResId)

        holder.itemView.setOnClickListener {
            onItemClick(item, holder.bindingAdapterPosition)
        }

        holder.itemView.setOnLongClickListener {
            onItemLongClick(item, holder.bindingAdapterPosition)
            true
        }
    }

    override fun getItemCount(): Int = items.size

    fun addItem(newItem: LibraryItem) {
        items.add(newItem)
        notifyItemInserted(items.size - 1)
    }

    fun removeItem(position: Int) {
        if (position in 0 until items.size) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun updateItem(position: Int, updatedItem: LibraryItem) {
        if (position in 0 until items.size) {
            items[position] = updatedItem
            notifyItemChanged(position)
        }
    }
}

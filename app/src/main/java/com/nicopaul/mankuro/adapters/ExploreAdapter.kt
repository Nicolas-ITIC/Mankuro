package com.nicopaul.mankuro.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nicopaul.mankuro.R
import com.nicopaul.mankuro.models.ExploreWork
import java.util.Locale

/** ADAPTER de la lista de resultados de Explorar (fila: explore_items.xml). */
class ExploreAdapter(
    private val items: MutableList<ExploreWork>,
    private val onItemClick: (ExploreWork, Int) -> Unit,
    private val onItemLongClick: (ExploreWork, Int) -> Unit
) : RecyclerView.Adapter<ExploreAdapter.ExploreViewHolder>() {

    /** HOLDER: referencias a las vistas de una fila. */
    class ExploreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivCover: ImageView = view.findViewById(R.id.ivExploreResultCover)
        val tvTitle: TextView = view.findViewById(R.id.tvExploreResultTitle)
        val tvType: TextView = view.findViewById(R.id.tvExploreResultType)
        val tvProgress: TextView = view.findViewById(R.id.tvExploreResultProgress)
        val tvScore: TextView = view.findViewById(R.id.tvExploreResultScore)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.explore_items, parent, false)
        return ExploreViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ExploreViewHolder, position: Int) {
        val work = items[position]
        val context = holder.itemView.context

        holder.tvTitle.text = work.title
        holder.tvType.text = work.type
        holder.tvProgress.text = context.getString(R.string.explore_progress_format, work.chapters)
        holder.tvScore.text = String.format(Locale.getDefault(), "%.1f", work.score)

        holder.itemView.setOnClickListener {
            val pos = holder.bindingAdapterPosition
            if (pos != RecyclerView.NO_POSITION) onItemClick(items[pos], pos)
        }
        holder.itemView.setOnLongClickListener {
            val pos = holder.bindingAdapterPosition
            if (pos != RecyclerView.NO_POSITION) onItemLongClick(items[pos], pos)
            true
        }
    }

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }
}
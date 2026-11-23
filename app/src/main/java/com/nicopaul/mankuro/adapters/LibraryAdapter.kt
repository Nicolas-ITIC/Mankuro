package com.nicopaul.mankuro.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nicopaul.mankuro.R
import com.nicopaul.mankuro.models.Work
import java.util.Locale

/**
 * ADAPTER: conecta la lista de datos (items) con las filas que se ven en pantalla.
 * Recibe dos "lambdas" (funciones) para avisar al Fragment cuando el usuario
 * toca una fila o la mantiene pulsada. Así el adapter no sabe nada de Toasts ni diálogos.
 */
class LibraryAdapter(
    private val items: MutableList<Work>,
    private val onItemClick: (Work, Int) -> Unit,
    private val onItemLongClick: (Work, Int) -> Unit
) : RecyclerView.Adapter<LibraryAdapter.WorkViewHolder>() {

    /**
     * HOLDER: guarda las referencias a las vistas de UNA fila (library_items.xml)
     * para no tener que hacer findViewById cada vez que se reutiliza la fila.
     */
    class WorkViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivCover: ImageView = view.findViewById(R.id.ivCover)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvType: TextView = view.findViewById(R.id.tvType)
        val tvProgress: TextView = view.findViewById(R.id.tvProgress)
        val tvRating: TextView = view.findViewById(R.id.tvRating)
    }

    // Crea una fila nueva a partir del layout del ítem
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.library_items, parent, false)
        return WorkViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    // Rellena una fila con los datos de la obra que toca en esa posición
    override fun onBindViewHolder(holder: WorkViewHolder, position: Int) {
        val work = items[position]
        val context = holder.itemView.context

        holder.tvTitle.text = work.title
        holder.tvType.text = work.type
        holder.tvProgress.text = context.getString(
            R.string.library_progress_format, work.currentChapter, work.totalChapters
        )
        holder.tvRating.text = String.format(Locale.getDefault(), "%.1f", work.rating)

        // Eventos de la fila. Se pide la posición actual en el momento del clic,
        // porque puede haber cambiado si se han añadido o borrado filas.
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

    // --- Operaciones sobre la lista: modifican los datos y avisan al RecyclerView ---

    fun addItem(work: Work) {
        items.add(0, work)
        notifyItemInserted(0)
    }

    fun updateItem(position: Int) {
        notifyItemChanged(position)
    }

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }
}

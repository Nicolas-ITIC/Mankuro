package com.nicopaul.mankuro.adapters

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.nicopaul.mankuro.R
import com.nicopaul.mankuro.models.Post
import com.nicopaul.mankuro.utils.toDisplayString

/** ADAPTER de la lista de publicaciones de la Comunidad (fila: community_post_items.xml). */
class PostAdapter(
    private val items: MutableList<Post>,
    private val onLikeClick: (Post, Int) -> Unit,
    private val onCommentsClick: (Post, Int) -> Unit,
    private val onPostLongClick: (Post, Int) -> Unit
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    /** HOLDER: referencias a las vistas de una fila. */
    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvAuthor: TextView = view.findViewById(R.id.tvPostAuthor)
        val tvDate: TextView = view.findViewById(R.id.tvPostDate)
        val tvDescription: TextView = view.findViewById(R.id.tvPostDescription)
        val ivImage: ImageView = view.findViewById(R.id.ivPostImage)
        val ivLike: ImageView = view.findViewById(R.id.ivPostLike)
        val tvLikes: TextView = view.findViewById(R.id.tvPostLikes)
        val ivComments: ImageView = view.findViewById(R.id.ivPostComments)
        val tvComments: TextView = view.findViewById(R.id.tvPostComments)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.community_post_items, parent, false)
        return PostViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = items[position]
        val context = holder.itemView.context

        holder.tvAuthor.text = post.author
        holder.tvDate.text = post.date.toDisplayString()   // función de extensión (utils/DateExtensions.kt)
        holder.tvDescription.text = post.description
        holder.tvLikes.text = post.likes.toString()
        holder.tvComments.text = post.comments.toString()

        // Estado del "me gusta": cambia el color Y se anuncia a los lectores de pantalla
        val tintColor = if (post.liked) R.color.mankuro_accent else R.color.on_surface_variant
        ImageViewCompat.setImageTintList(
            holder.ivLike, ColorStateList.valueOf(ContextCompat.getColor(context, tintColor))
        )
        ViewCompat.setStateDescription(
            holder.ivLike,
            context.getString(
                if (post.liked) R.string.community_liked_state else R.string.community_not_liked_state
            )
        )

        holder.ivLike.setOnClickListener {
            val pos = holder.bindingAdapterPosition
            if (pos != RecyclerView.NO_POSITION) onLikeClick(items[pos], pos)
        }
        holder.ivComments.setOnClickListener {
            val pos = holder.bindingAdapterPosition
            if (pos != RecyclerView.NO_POSITION) onCommentsClick(items[pos], pos)
        }
        holder.itemView.setOnLongClickListener {
            val pos = holder.bindingAdapterPosition
            if (pos != RecyclerView.NO_POSITION) onPostLongClick(items[pos], pos)
            true
        }
    }

    fun addItem(post: Post) {
        items.add(0, post)
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
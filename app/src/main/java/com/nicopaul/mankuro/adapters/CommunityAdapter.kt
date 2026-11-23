package com.nicopaul.mankuro.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nicopaul.mankuro.R
import com.nicopaul.mankuro.models.CommunityPost

class CommunityAdapter(
    private val posts: MutableList<CommunityPost>,
    private val onPostClick: (CommunityPost, Int) -> Unit,
    private val onPostLongClick: (CommunityPost, Int) -> Unit
) : RecyclerView.Adapter<CommunityAdapter.CommunityViewHolder>() {

    class CommunityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvAuthor: TextView = itemView.findViewById(R.id.tvPostAuthor)
        val tvDescription: TextView = itemView.findViewById(R.id.tvPostDescription)
        val ivPostImage: ImageView = itemView.findViewById(R.id.ivPostImage)
        val tvLikes: TextView = itemView.findViewById(R.id.tvPostLikes)
        val tvComments: TextView = itemView.findViewById(R.id.tvPostComments)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.community_post_items, parent, false)
        return CommunityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommunityViewHolder, position: Int) {
        val post = posts[position]
        holder.tvAuthor.text = post.author
        holder.tvDescription.text = post.description
        holder.tvLikes.text = "${post.likesCount} Me gusta"
        holder.tvComments.text = "${post.commentsCount} comentarios"
        holder.ivPostImage.setImageResource(post.imageResId)

        holder.itemView.setOnClickListener {
            onPostClick(post, holder.bindingAdapterPosition)
        }

        holder.itemView.setOnLongClickListener {
            onPostLongClick(post, holder.bindingAdapterPosition)
            true
        }
    }

    override fun getItemCount(): Int = posts.size

    fun addPost(post: CommunityPost) {
        posts.add(0, post)
        notifyItemInserted(0)
    }

    fun removePost(position: Int) {
        if (position in 0 until posts.size) {
            posts.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun updatePost(position: Int, updatedPost: CommunityPost) {
        if (position in 0 until posts.size) {
            posts[position] = updatedPost
            notifyItemChanged(position)
        }
    }
}

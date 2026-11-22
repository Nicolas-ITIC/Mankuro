package com.nicopaul.mankuro.MankuroFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nicopaul.mankuro.R
import com.nicopaul.mankuro.adapters.PostAdapter
import com.nicopaul.mankuro.models.Post
import com.nicopaul.mankuro.models.SampleData

class CommunityFragment : Fragment() {

    private lateinit var adapter: PostAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.community_main_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvPosts = view.findViewById<RecyclerView>(R.id.rvCommunityPosts)
        val btnCreatePost = view.findViewById<View>(R.id.btnCreatePost)

        adapter = PostAdapter(
            items = SampleData.communityPosts(),
            onLikeClick = { post, position -> toggleLike(post, position) },
            onCommentsClick = { post, _ -> openComments(post) },
            onPostLongClick = { post, position -> confirmDelete(post, position) }
        )
        rvPosts.layoutManager = LinearLayoutManager(requireContext())
        rvPosts.adapter = adapter

        btnCreatePost.setOnClickListener { addPost(rvPosts) }
    }

    // AÑADIR (botón "Crear publicación")
    private fun addPost(rvPosts: RecyclerView) {
        val post = SampleData.newPost()
        adapter.addItem(post)
        rvPosts.scrollToPosition(0)
        Log.d(TAG, "Publicación añadida: ${post.id}")
        Toast.makeText(requireContext(), R.string.community_post_added, Toast.LENGTH_SHORT).show()
    }

    // MODIFICAR (tocar el corazón: me gusta / quitar me gusta)
    private fun toggleLike(post: Post, position: Int) {
        post.liked = !post.liked
        post.likes += if (post.liked) 1 else -1
        adapter.updateItem(position)
        Log.d(TAG, "Me gusta en la publicación ${post.id}: ${post.liked} (${post.likes})")
    }

    // Tocar el icono de comentarios
    private fun openComments(post: Post) {
        Log.d(TAG, "Comentarios de la publicación ${post.id}")
        Toast.makeText(
            requireContext(), getString(R.string.community_comments_open, post.author), Toast.LENGTH_SHORT
        ).show()
    }

    // ELIMINAR (mantener pulsada una publicación, con confirmación)
    private fun confirmDelete(post: Post, position: Int) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.community_delete_title)
            .setMessage(getString(R.string.community_delete_message, post.author))
            .setPositiveButton(R.string.community_delete) { _, _ ->
                adapter.removeItem(position)
                Log.d(TAG, "Publicación eliminada: ${post.id}")
                Toast.makeText(requireContext(), R.string.community_post_deleted, Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    companion object {
        private const val TAG = "CommunityFragment"
    }
}
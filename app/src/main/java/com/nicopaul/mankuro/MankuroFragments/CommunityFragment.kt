package com.nicopaul.mankuro.MankuroFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nicopaul.mankuro.R
import com.nicopaul.mankuro.adapters.CommunityAdapter
import com.nicopaul.mankuro.models.CommunityPost

class CommunityFragment : Fragment() {

    private lateinit var adapter: CommunityAdapter
    private val postsList = mutableListOf<CommunityPost>()
    private var newPostCounter = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.community_main_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (postsList.isEmpty()) {
            postsList.addAll(getSamplePosts())
        }

        val rvCommunityPosts = view.findViewById<RecyclerView>(R.id.rvCommunityPosts)
        rvCommunityPosts.layoutManager = LinearLayoutManager(requireContext())

        adapter = CommunityAdapter(
            posts = postsList,
            onPostClick = { post, position ->
                post.likesCount += 1
                adapter.updatePost(position, post)

                val message = "Modificado post (Like añadido): Autor ${post.author}"
                Log.d("CommunityFragment", message)
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            },
            onPostLongClick = { post, position ->
                val message = "Eliminado post de: ${post.author}"
                Log.d("CommunityFragment", message)
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

                adapter.removePost(position)
            }
        )
        rvCommunityPosts.adapter = adapter

        val btnCreatePost = view.findViewById<LinearLayout>(R.id.btnCreatePost)
        btnCreatePost?.setOnClickListener {
            val newPost = CommunityPost(
                id = System.currentTimeMillis().toInt(),
                author = "@usuario_otaku_$newPostCounter",
                description = "¡Recomiendo muchísimo leer este nuevo manga! Publicación de prueba #$newPostCounter",
                likesCount = 0,
                commentsCount = 0
            )
            newPostCounter++

            adapter.addPost(newPost)

            val message = "Añadido nuevo post de: ${newPost.author}"
            Log.d("CommunityFragment", message)
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun getSamplePosts(): List<CommunityPost> {
        return listOf(
            CommunityPost(
                1,
                "@otaku_reader",
                "¡El último capítulo de Solo Leveling ha sido alucinante! ¿Qué os ha parecido el final?",
                142,
                28
            ),
            CommunityPost(
                2,
                "@manga_fanatic",
                "Buscando recomendaciones de Manhwas de acción y fantasía parecidos a Tower of God.",
                89,
                15
            ),
            CommunityPost(
                3,
                "@anime_lover",
                "Top 5 mangas de la semana en Mankuro. ¡No te los pierdas!",
                210,
                45
            )
        )
    }
}

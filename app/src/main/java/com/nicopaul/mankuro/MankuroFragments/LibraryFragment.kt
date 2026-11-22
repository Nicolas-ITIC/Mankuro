package com.nicopaul.mankuro.MankuroFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nicopaul.mankuro.R
import com.nicopaul.mankuro.adapters.LibraryAdapter
import com.nicopaul.mankuro.models.LibraryItem

class LibraryFragment : Fragment() {

    private lateinit var adapter: LibraryAdapter
    private val libraryList = mutableListOf<LibraryItem>()
    private var newItemCounter = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.library_main_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (libraryList.isEmpty()) {
            libraryList.addAll(getSampleData())
        }

        val rvLibrary = view.findViewById<RecyclerView>(R.id.rvLibrary)
        rvLibrary.layoutManager = LinearLayoutManager(requireContext())

        adapter = LibraryAdapter(
            items = libraryList,
            onItemClick = { item, position ->
                item.progress = "Capítulo actualizado (${(10..100).random()})"
                adapter.updateItem(position, item)

                val message = "Modificado elemento: ${item.title}"
                Log.d("LibraryFragment", message)
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            },
            onItemLongClick = { item, position ->
                val message = "Eliminado elemento: ${item.title}"
                Log.d("LibraryFragment", message)
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

                adapter.removeItem(position)
            }
        )
        rvLibrary.adapter = adapter

        val btnAdd = view.findViewById<ImageView>(R.id.btnLibraryAdd)
        btnAdd?.setOnClickListener {
            val newItem = LibraryItem(
                id = System.currentTimeMillis().toInt(),
                title = "Manga de prueba #$newItemCounter",
                type = if (newItemCounter % 2 == 0) "Manhwa" else "Manga",
                progress = "Capítulo 1 leídos",
                rating = "8.5"
            )
            newItemCounter++

            adapter.addItem(newItem)

            val message = "Añadido elemento: ${newItem.title}"
            Log.d("LibraryFragment", message)
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun getSampleData(): List<LibraryItem> {
        return listOf(
            LibraryItem(1, "Solo Leveling", "Manhwa", "Capítulo 179 leídos", "9.8"),
            LibraryItem(2, "One Piece", "Manga", "Capítulo 1090 leídos", "9.5"),
            LibraryItem(3, "Jujutsu Kaisen", "Manga", "Capítulo 250 leídos", "9.1"),
            LibraryItem(4, "Tower of God", "Manhwa", "Capítulo 580 leídos", "9.3"),
            LibraryItem(5, "Chainsaw Man", "Manga", "Capítulo 150 leídos", "8.9"),
            LibraryItem(6, "Berserk", "Manga", "Capítulo 375 leídos", "9.9")
        )
    }
}

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
import com.nicopaul.mankuro.models.Work

class LibraryFragment : Fragment() {

    private lateinit var adapter: LibraryAdapter
    private val libraryList = mutableListOf<Work>()
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
                item.currentChapter += 1
                adapter.updateItem(position)

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
            val newItem = Work(
                id = System.currentTimeMillis().toInt(),
                title = "Manga de prueba #$newItemCounter",
                type = if (newItemCounter % 2 == 0) "Manhwa" else "Manga",
                currentChapter = 1,
                totalChapters = 50,
                rating = 8.5
            )
            newItemCounter++

            adapter.addItem(newItem)

            val message = "Añadido elemento: ${newItem.title}"
            Log.d("LibraryFragment", message)
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }

        val btnFilters = view.findViewById<ImageView>(R.id.btnLibraryFilters)
        btnFilters?.setOnClickListener {
            Toast.makeText(requireContext(), "Filtros de biblioteca seleccionados", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getSampleData(): List<Work> {
        return listOf(
            Work(1, "Solo Leveling", "Manhwa", 179, 179, 9.8),
            Work(2, "One Piece", "Manga", 1090, 1110, 9.5),
            Work(3, "Jujutsu Kaisen", "Manga", 250, 271, 9.1),
            Work(4, "Tower of God", "Manhwa", 580, 600, 9.3),
            Work(5, "Chainsaw Man", "Manga", 150, 170, 8.9),
            Work(6, "Berserk", "Manga", 375, 375, 9.9)
        )
    }
}

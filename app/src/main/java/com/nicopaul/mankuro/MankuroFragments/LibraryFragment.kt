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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.nicopaul.mankuro.R
import com.nicopaul.mankuro.adapters.LibraryAdapter
import com.nicopaul.mankuro.models.SampleData
import com.nicopaul.mankuro.models.Work

class LibraryFragment : Fragment() {

    private lateinit var adapter: LibraryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.library_main_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvLibrary = view.findViewById<RecyclerView>(R.id.rvLibrary)
        val fabAdd = view.findViewById<FloatingActionButton>(R.id.fabAddWork)

        adapter = LibraryAdapter(
            items = SampleData.libraryWorks(),
            onItemClick = { work, position -> modifyWork(work, position) },
            onItemLongClick = { work, position -> confirmDelete(work, position) }
        )
        rvLibrary.layoutManager = LinearLayoutManager(requireContext())
        rvLibrary.adapter = adapter

        fabAdd.setOnClickListener { addWork(rvLibrary) }
    }

    // AÑADIR (botón flotante)
    private fun addWork(rvLibrary: RecyclerView) {
        val work = SampleData.newWork()
        adapter.addItem(work)
        rvLibrary.scrollToPosition(0)
        Log.d(TAG, "Añadida: ${work.title}")
        Toast.makeText(
            requireContext(), getString(R.string.library_item_added, work.title), Toast.LENGTH_SHORT
        ).show()
    }

    // MODIFICAR (al tocar una fila: avanza un capítulo)
    private fun modifyWork(work: Work, position: Int) {
        if (work.currentChapter < work.totalChapters) work.currentChapter++
        adapter.updateItem(position)
        Log.d(TAG, "Modificada: ${work.title} -> capítulo ${work.currentChapter}")
        Toast.makeText(
            requireContext(), getString(R.string.library_item_modified, work.title), Toast.LENGTH_SHORT
        ).show()
    }

    // ELIMINAR (al mantener pulsada una fila, con confirmación)
    private fun confirmDelete(work: Work, position: Int) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.library_delete_title)
            .setMessage(getString(R.string.library_delete_message, work.title))
            .setPositiveButton(R.string.library_delete) { _, _ ->
                adapter.removeItem(position)
                Log.d(TAG, "Eliminada: ${work.title}")
                Toast.makeText(
                    requireContext(), getString(R.string.library_item_deleted, work.title), Toast.LENGTH_SHORT
                ).show()
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    companion object {
        private const val TAG = "LibraryFragment"
    }
}
package com.nicopaul.mankuro.MankuroFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nicopaul.mankuro.R
import com.nicopaul.mankuro.adapters.ExploreAdapter
import com.nicopaul.mankuro.models.ExploreWork
import com.nicopaul.mankuro.models.SampleData

/** Paso 3 de Explorar: lista de resultados (RecyclerView). Layout: explore_results.xml */
class ExploreResultsFragment : Fragment() {

    private lateinit var adapter: ExploreAdapter
    private var type: String = ""
    private var category: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        type = arguments?.getString(ARG_TYPE).orEmpty()
        category = arguments?.getString(ARG_CATEGORY).orEmpty()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.explore_results, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Título tipo "Manga > Top puntuaciones"
        view.findViewById<TextView>(R.id.tvExploreResultsTitle).text =
            getString(R.string.explore_breadcrumb_format, type, category)

        view.findViewById<View>(R.id.btnExploreResultsBack).setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val rvResults = view.findViewById<RecyclerView>(R.id.rvExploreResults)
        adapter = ExploreAdapter(
            items = SampleData.exploreWorks(),
            onItemClick = { work, _ -> addToLibrary(work) },
            onItemLongClick = { work, position -> hideResult(work, position) }
        )
        rvResults.layoutManager = LinearLayoutManager(requireContext())
        rvResults.adapter = adapter
    }

    // AÑADIR (tocar un resultado: lo añade a la biblioteca)
    private fun addToLibrary(work: ExploreWork) {
        Log.d(TAG, "Añadida a la biblioteca: ${work.title}")
        Toast.makeText(
            requireContext(), getString(R.string.explore_added_to_library, work.title), Toast.LENGTH_SHORT
        ).show()
    }

    // ELIMINAR (mantener pulsado: oculta el resultado de la lista)
    private fun hideResult(work: ExploreWork, position: Int) {
        adapter.removeItem(position)
        Log.d(TAG, "Resultado ocultado: ${work.title}")
        Toast.makeText(
            requireContext(), getString(R.string.explore_hidden, work.title), Toast.LENGTH_SHORT
        ).show()
    }

    companion object {
        private const val TAG = "ExploreResults"
        private const val ARG_TYPE = "type"
        private const val ARG_CATEGORY = "category"

        fun newInstance(type: String, category: String) = ExploreResultsFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_TYPE, type)
                putString(ARG_CATEGORY, category)
            }
        }
    }
}
package com.nicopaul.mankuro.MankuroFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.nicopaul.mankuro.R

/** Paso 2 de Explorar: elegir la categoría (Top puntuaciones, Recomendados...). Layout: explore_second_page.xml */
class ExploreCategoryFragment : Fragment() {

    private var type: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        type = arguments?.getString(ARG_TYPE).orEmpty()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.explore_second_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // El título muestra el tipo elegido en el paso anterior
        view.findViewById<TextView>(R.id.tvExploreCategoryTitle).text = type

        view.findViewById<View>(R.id.btnExploreCategoryBack).setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val categories = mapOf(
            R.id.btnTopScores to R.string.explore_top_scores,
            R.id.btnTopPopularity to R.string.explore_top_popularity,
            R.id.btnRecommended to R.string.explore_recommended,
            R.id.btnGenres to R.string.explore_genres,
            R.id.btnOngoing to R.string.explore_ongoing
        )
        categories.forEach { (buttonId, labelRes) ->
            view.findViewById<View>(buttonId).setOnClickListener {
                openResults(getString(labelRes))
            }
        }
    }

    private fun openResults(category: String) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.mainContent, ExploreResultsFragment.newInstance(type, category))
            .addToBackStack(null)
            .commit()
    }

    companion object {
        private const val ARG_TYPE = "type"

        // Forma habitual de pasar datos a un Fragment: por argumentos (Bundle)
        fun newInstance(type: String) = ExploreCategoryFragment().apply {
            arguments = Bundle().apply { putString(ARG_TYPE, type) }
        }
    }
}
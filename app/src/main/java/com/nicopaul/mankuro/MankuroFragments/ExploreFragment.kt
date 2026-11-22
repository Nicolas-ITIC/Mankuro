package com.nicopaul.mankuro.MankuroFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nicopaul.mankuro.R

/** Paso 1 de Explorar: elegir el tipo (Manga, Manhwa o ambos). Layout: explore_main_page.xml */
class ExploreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.explore_main_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Cada botón se asocia al texto del tipo que representa
        val types = mapOf(
            R.id.btnExploreManga to R.string.explore_manga,
            R.id.btnExploreManhwa to R.string.explore_manhwa,
            R.id.btnExploreBoth to R.string.explore_both
        )
        types.forEach { (buttonId, labelRes) ->
            view.findViewById<View>(buttonId).setOnClickListener {
                openCategory(getString(labelRes))
            }
        }
    }

    // Va al paso 2 y lo añade a la pila para poder volver con la flecha
    private fun openCategory(type: String) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.mainContent, ExploreCategoryFragment.newInstance(type))
            .addToBackStack(null)
            .commit()
    }
}
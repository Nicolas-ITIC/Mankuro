package com.nicopaul.mankuro.MankuroFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.nicopaul.mankuro.R

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnLogout = view.findViewById<ImageButton>(R.id.btnProfileLogout)
        btnLogout?.setOnClickListener {
            Toast.makeText(requireContext(), "Cerrando sesión...", Toast.LENGTH_SHORT).show()
        }
    }
}

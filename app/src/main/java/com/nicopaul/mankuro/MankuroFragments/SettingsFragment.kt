package com.nicopaul.mankuro.MankuroFragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.google.android.material.switchmaterial.SwitchMaterial
import com.nicopaul.mankuro.Mankuro.AccessibilityActivity
import com.nicopaul.mankuro.R
import com.nicopaul.mankuro.ThemePrefs

class SettingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val switchTheme = view.findViewById<SwitchMaterial>(R.id.switchTheme)

        switchTheme.isChecked = ThemePrefs.isDark(requireContext())

        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            ThemePrefs.setDark(requireContext(), isChecked)
        }


        val rowAccessibility = view.findViewById<LinearLayout>(R.id.rowAccessibility)
        rowAccessibility.setOnClickListener {
            val intent = Intent(requireContext(), AccessibilityActivity::class.java)
            startActivity(intent)
        }
    }

}
package com.nicopaul.mankuro.Mankuro

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nicopaul.mankuro.MankuroFragments.CommunityFragment
import com.nicopaul.mankuro.MankuroFragments.ExploreFragment
import com.nicopaul.mankuro.MankuroFragments.LibraryFragment
import com.nicopaul.mankuro.MankuroFragments.ProfileFragment
import com.nicopaul.mankuro.MankuroFragments.SettingsFragment
import com.nicopaul.mankuro.R
import com.nicopaul.mankuro.utils.AccessibilityHelper

class MainPageActivity : AppCompatActivity() {

    private var initialHighContrast = false
    private var initialTextSizeIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        AccessibilityHelper.applyAccessibility(this)
        super.onCreate(savedInstanceState)
        initialHighContrast = AccessibilityHelper.isHighContrastEnabled(this)
        initialTextSizeIndex = AccessibilityHelper.getTextSizeIndex(this)

        setContentView(R.layout.activity_main_page)

        val mainLayout = findViewById<View>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(mainLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainContent, LibraryFragment())
                .commit()
        }


        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        bottomNavigation.setOnItemSelectedListener { item ->
            val selectedFragment: Fragment? = when (item.itemId) {
                R.id.nav_library -> LibraryFragment()
                R.id.nav_explore -> ExploreFragment()
                R.id.nav_profile -> ProfileFragment()
                R.id.nav_settings -> SettingsFragment()
                R.id.nav_community -> CommunityFragment()
                else -> null
            }

            if (selectedFragment != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.mainContent, selectedFragment)
                    .commit()
                true
            } else {
                false
            }
        }

    }

    override fun onResume() {
        super.onResume()
        val currentHighContrast = AccessibilityHelper.isHighContrastEnabled(this)
        val currentTextSizeIndex = AccessibilityHelper.getTextSizeIndex(this)

        if (currentHighContrast != initialHighContrast || currentTextSizeIndex != initialTextSizeIndex) {
            recreate()
        }
    }

}
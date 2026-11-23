package com.nicopaul.mankuro.Mankuro

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nicopaul.mankuro.MankuroFragments.CommunityFragment
import com.nicopaul.mankuro.MankuroFragments.ExploreFragment
import com.nicopaul.mankuro.MankuroFragments.HomeFragment
import com.nicopaul.mankuro.MankuroFragments.LibraryFragment
import com.nicopaul.mankuro.MankuroFragments.ProfileFragment
import com.nicopaul.mankuro.MankuroFragments.SettingsFragment
import com.nicopaul.mankuro.R
import com.nicopaul.mankuro.utils.AccessibilityHelper

class MainPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AccessibilityHelper.applyAccessibility(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        val mainLayout = findViewById<View>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(mainLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        bottomNavigation.setOnItemSelectedListener { item ->
            val selectedFragment: Fragment? = when (item.itemId) {
                R.id.nav_home -> HomeFragment()
                R.id.nav_library -> LibraryFragment()
                R.id.nav_explore -> ExploreFragment()
                R.id.nav_community -> CommunityFragment()
                R.id.nav_profile -> ProfileFragment()
                R.id.nav_settings -> SettingsFragment()
                else -> null
            }

            if (selectedFragment != null) {
                supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)   // <-- línea nueva

                supportFragmentManager.beginTransaction()
                    .replace(R.id.mainContent, selectedFragment)
                    .commit()
                true
            } else {
                false
            }
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainContent, HomeFragment())
                .commit()
        }
    }

    fun selectNavigationItem(itemId: Int) {
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigation.selectedItemId = itemId
    }
}

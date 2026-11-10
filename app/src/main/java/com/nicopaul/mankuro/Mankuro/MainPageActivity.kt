package com.nicopaul.mankuro.Mankuro

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nicopaul.mankuro.R

class MainPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main_page)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        if (savedInstanceState == null) {
            loadFragment(LibraryFragment())
            // Asegurarnos de que el icono correspondiente empiece seleccionado
            bottomNavigation.selectedItemId = R.id.nav_library
        }

    }
}
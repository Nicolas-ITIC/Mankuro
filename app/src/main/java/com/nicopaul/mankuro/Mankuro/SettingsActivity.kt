package com.nicopaul.mankuro.Mankuro

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.nicopaul.mankuro.R
import com.nicopaul.mankuro.utils.AccessibilityHelper

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AccessibilityHelper.applyAccessibility(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)

        val root = findViewById<View>(R.id.scrollSettings)
        if (root != null) {
            ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack?.setOnClickListener {
            AccessibilityHelper.performHapticFeedback(it)
            finish()
        }

        val rowAccessibility = findViewById<LinearLayout>(R.id.rowAccessibility)
        rowAccessibility?.setOnClickListener {
            AccessibilityHelper.performHapticFeedback(it)
            startActivity(Intent(this, AccessibilityActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        AccessibilityHelper.applyAccessibility(this)
    }
}

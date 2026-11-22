package com.nicopaul.mankuro.Mankuro

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nicopaul.mankuro.R
import com.nicopaul.mankuro.utils.AccessibilityHelper

class AccessibilityActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var rowTextSize: LinearLayout
    private lateinit var tvCurrentTextSize: TextView
    private lateinit var switchHighContrast: SwitchCompat
    private lateinit var switchReduceMotion: SwitchCompat
    private lateinit var switchHapticFeedback: SwitchCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        AccessibilityHelper.applyAccessibility(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_accessibility)

        val root = findViewById<View>(R.id.scrollAccessibility)
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        setupListeners()
    }

    private fun initViews() {
        btnBack = findViewById(R.id.btnBackAccessibility)
        rowTextSize = findViewById(R.id.rowTextSize)
        tvCurrentTextSize = findViewById(R.id.tvCurrentTextSize)
        switchHighContrast = findViewById(R.id.switchHighContrast)
        switchReduceMotion = findViewById(R.id.switchReduceMotion)
        switchHapticFeedback = findViewById(R.id.switchHapticFeedback)

        updateTextSizeLabel()
        switchHighContrast.isChecked = AccessibilityHelper.isHighContrastEnabled(this)
        switchReduceMotion.isChecked = AccessibilityHelper.isReduceMotionEnabled(this)
        switchHapticFeedback.isChecked = AccessibilityHelper.isHapticFeedbackEnabled(this)
    }

    private fun setupListeners() {
        btnBack.setOnClickListener {
            AccessibilityHelper.performHapticFeedback(it)
            finish()
        }

        rowTextSize.setOnClickListener {
            AccessibilityHelper.performHapticFeedback(it)
            showTextSizeDialog()
        }

        switchHighContrast.setOnCheckedChangeListener { buttonView, isChecked ->
            AccessibilityHelper.performHapticFeedback(buttonView)
            AccessibilityHelper.setHighContrastEnabled(this, isChecked)
            recreate()
        }

        switchReduceMotion.setOnCheckedChangeListener { buttonView, isChecked ->
            AccessibilityHelper.performHapticFeedback(buttonView)
            AccessibilityHelper.setReduceMotionEnabled(this, isChecked)
            if (isChecked) {
                window.setWindowAnimations(0)
            }
        }

        switchHapticFeedback.setOnCheckedChangeListener { buttonView, isChecked ->
            AccessibilityHelper.setHapticFeedbackEnabled(this, isChecked)
            if (isChecked) {
                AccessibilityHelper.performHapticFeedback(buttonView)
            }
        }
    }

    private fun showTextSizeDialog() {
        val currentIndex = AccessibilityHelper.getTextSizeIndex(this)
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.text_size)
            .setSingleChoiceItems(AccessibilityHelper.TEXT_SIZE_LABELS, currentIndex) { dialog, which ->
                AccessibilityHelper.setTextSizeIndex(this, which)
                updateTextSizeLabel()
                dialog.dismiss()
                recreate()
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    private fun updateTextSizeLabel() {
        val label = AccessibilityHelper.getTextSizeLabel(this)
        tvCurrentTextSize.text = getString(R.string.font_size_state_format, label)
    }
}

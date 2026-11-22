package com.nicopaul.mankuro.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.view.HapticFeedbackConstants
import android.view.View
import androidx.core.content.edit
import com.nicopaul.mankuro.R

object AccessibilityHelper {
    private const val PREFS_NAME = "mankuro_accessibility_prefs"
    private const val KEY_TEXT_SIZE_INDEX = "text_size_index"
    private const val KEY_HIGH_CONTRAST = "high_contrast"
    private const val KEY_REDUCE_MOTION = "reduce_motion"
    private const val KEY_HAPTIC_FEEDBACK = "haptic_feedback"

    val TEXT_SIZE_LABELS = arrayOf("Pequeño", "Normal", "Grande", "Muy grande")
    val TEXT_SIZE_SCALES = floatArrayOf(0.85f, 1.0f, 1.15f, 1.30f)

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    // --- Tamaño de texto ---
    fun getTextSizeIndex(context: Context): Int {
        return getPrefs(context).getInt(KEY_TEXT_SIZE_INDEX, 1) // 1 = Normal por defecto
    }

    fun setTextSizeIndex(context: Context, index: Int) {
        getPrefs(context).edit { putInt(KEY_TEXT_SIZE_INDEX, index) }
    }

    fun getFontScale(context: Context): Float {
        val index = getTextSizeIndex(context).coerceIn(0, TEXT_SIZE_SCALES.lastIndex)
        return TEXT_SIZE_SCALES[index]
    }

    fun getTextSizeLabel(context: Context): String {
        val index = getTextSizeIndex(context).coerceIn(0, TEXT_SIZE_LABELS.lastIndex)
        return TEXT_SIZE_LABELS[index]
    }

    // --- Alto contraste ---
    fun isHighContrastEnabled(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_HIGH_CONTRAST, false)
    }

    fun setHighContrastEnabled(context: Context, enabled: Boolean) {
        getPrefs(context).edit { putBoolean(KEY_HIGH_CONTRAST, enabled) }
    }

    // --- Reducir animaciones ---
    fun isReduceMotionEnabled(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_REDUCE_MOTION, false)
    }

    fun setReduceMotionEnabled(context: Context, enabled: Boolean) {
        getPrefs(context).edit { putBoolean(KEY_REDUCE_MOTION, enabled) }
    }

    // --- Vibración al pulsar (Haptic Feedback) ---
    fun isHapticFeedbackEnabled(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_HAPTIC_FEEDBACK, true)
    }

    fun setHapticFeedbackEnabled(context: Context, enabled: Boolean) {
        getPrefs(context).edit { putBoolean(KEY_HAPTIC_FEEDBACK, enabled) }
    }

    fun performHapticFeedback(view: View) {
        if (isHapticFeedbackEnabled(view.context)) {
            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
        }
    }

    // --- Aplicar todas las opciones de accesibilidad a una Activity ---
    fun applyAccessibility(activity: Activity) {
        // Tema de alto contraste
        if (isHighContrastEnabled(activity)) {
            activity.setTheme(R.style.Theme_Mankuro_HighContrast)
        }

        // Escala de fuente (Tamaño de texto)
        val scale = getFontScale(activity)
        val config = activity.resources.configuration
        if (config.fontScale != scale) {
            config.fontScale = scale
            @Suppress("DEPRECATION")
            activity.resources.updateConfiguration(config, activity.resources.displayMetrics)
        }

        // Reducir animaciones
        if (isReduceMotionEnabled(activity)) {
            activity.window.setWindowAnimations(0)
        }
    }
}

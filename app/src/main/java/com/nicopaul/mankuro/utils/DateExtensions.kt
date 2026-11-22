package com.nicopaul.mankuro.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * FUNCIÓN DE EXTENSIÓN: añade un método a la clase Date sin modificarla.
 * Uso:  post.date.toDisplayString()   ->  "22 nov 2026"
 */
fun Date.toDisplayString(): String =
    SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(this)
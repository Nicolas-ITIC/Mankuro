package com.nicopaul.mankuro.models

/** Una obra que aparece en los resultados de Explorar (catálogo global, no la biblioteca del usuario). */
data class ExploreWork(
    val id: Int,
    val title: String,
    val type: String,
    val chapters: Int,
    val score: Double
)
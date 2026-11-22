package com.nicopaul.mankuro.models

/**
 * Una obra (manga o manhwa) de la biblioteca del usuario.
 * "data class": Kotlin genera solo equals(), toString(), copy()...
 * Las propiedades "var" son las que se pueden modificar desde la lista.
 */
data class Work(
    val id: Int,
    var title: String,
    var type: String,
    var currentChapter: Int,
    val totalChapters: Int,
    var rating: Double
)

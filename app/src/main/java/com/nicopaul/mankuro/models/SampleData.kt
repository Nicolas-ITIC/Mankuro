package com.nicopaul.mankuro.models

import java.util.Date

/**
 * "object": singleton de Kotlin. Guarda los datos de prueba de las tres listas.
 * SUSTITUYE al SampleData.kt de la Biblioteca (mantiene sus funciones).
 */
object SampleData {

    private var nextId = 100

    // ---------- Biblioteca ----------
    fun libraryWorks(): MutableList<Work> = mutableListOf(
        Work(1, "Solo Leveling", "Manhwa", 120, 200, 9.5),
        Work(2, "One Piece", "Manga", 1050, 1120, 9.0),
        Work(3, "Berserk", "Manga", 364, 374, 9.8),
        Work(4, "Tower of God", "Manhwa", 300, 600, 8.7),
        Work(5, "Vagabond", "Manga", 327, 327, 9.6)
    )

    fun newWork(): Work {
        val id = nextId++
        return Work(id, "Nueva obra $id", "Manga", 0, 100, 0.0)
    }

    // ---------- Explorar ----------
    fun exploreWorks(): MutableList<ExploreWork> = mutableListOf(
        ExploreWork(1, "Chainsaw Man", "Manga", 97, 8.9),
        ExploreWork(2, "The Beginning After the End", "Manhwa", 250, 8.6),
        ExploreWork(3, "Vinland Saga", "Manga", 210, 9.3),
        ExploreWork(4, "Omniscient Reader", "Manhwa", 190, 9.0),
        ExploreWork(5, "Monster", "Manga", 162, 9.4)
    )

    // ---------- Comunidad ----------
    private fun daysAgo(days: Int) = Date(System.currentTimeMillis() - days * 24L * 60 * 60 * 1000)

    fun communityPosts(): MutableList<Post> = mutableListOf(
        Post(1, "Mankurouser", "¿Qué manhwa me recomendáis para empezar?", 24, 8, false, daysAgo(0)),
        Post(2, "LectorNocturno", "Acabo de terminar Berserk y sigo sin palabras.", 58, 21, true, daysAgo(1)),
        Post(3, "AkiraFan", "Hilo semanal: ¿qué estáis leyendo esta semana?", 12, 30, false, daysAgo(3)),
        Post(4, "PandaLector", "Mi lista de pendientes ya tiene más de 50 obras...", 41, 12, false, daysAgo(6))
    )

    fun newPost(): Post {
        val id = nextId++
        return Post(id, "Tú", "Publicación de prueba número $id", 0, 0, false, Date())
    }
}

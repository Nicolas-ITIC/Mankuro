package com.nicopaul.mankuro.models

import java.util.Date

/** Una publicación de la comunidad. "liked" indica si el usuario le ha dado me gusta. */
data class Post(
    val id: Int,
    val author: String,
    val description: String,
    var likes: Int,
    var comments: Int,
    var liked: Boolean = false,
    val date: Date = Date()
)
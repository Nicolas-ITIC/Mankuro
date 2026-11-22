package com.nicopaul.mankuro.models

import android.R

data class LibraryItem(
    val id: Int,
    var title: String,
    var type: String,
    var progress: String,
    var rating: String,
    val imageResId: Int = R.drawable.ic_menu_gallery
)

package com.nicopaul.mankuro.models

import android.R

data class CommunityPost(
    val id: Int,
    var author: String,
    var description: String,
    var likesCount: Int,
    var commentsCount: Int,
    val imageResId: Int = R.drawable.ic_menu_gallery
)

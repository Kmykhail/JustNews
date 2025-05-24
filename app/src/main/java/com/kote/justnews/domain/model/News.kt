package com.kote.justnews.domain.model

import java.util.Date

data class News(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String? = null,
    val url: String,
    val source: Source,
    val author: String? = null,
    val publishedAt: Date
) {
    data class Source(
        val id: String?,
        val name: String?
    )
}
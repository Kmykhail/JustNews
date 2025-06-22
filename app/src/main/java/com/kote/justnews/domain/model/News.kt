package com.kote.justnews.domain.model

import java.time.LocalDateTime
import java.util.Date

data class News(
    val title: String,
    val description: String,
    val imageUrl: String,
    val link: String,
    val sourceUrl: String,
    val sourceName: String,
    val publishedAt: LocalDateTime
)
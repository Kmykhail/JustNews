package com.kote.justnews.data.api

import kotlinx.serialization.Serializable

@Serializable
data class NewsResponseDto(
    val articles: List<NewsDto>
)

@Serializable
data class NewsDto(
    val source: SourceDto? = null,
    val author: String? = null,
    val title: String? = null,
    val description: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    val publishedAt: String? = null,
)

@Serializable
data class SourceDto(
    val id: String? = null,
    val name: String? = null
)

package com.kote.justnews.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "newsCache")
data class NewsCache(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val link: String,
    val imageUrl: String? = null,
    val description: String,
    val sourceUrl: String?,
    val sourceName: String?,
    val publishedAt: String,
    val cachedAt: Long
)
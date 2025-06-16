package com.kote.justnews.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsCacheDao {
    @Query("SELECT * FROM newsCache ORDER BY publishedAt DESC LIMIT :limit")
    suspend fun getRecentNews(limit: Int = 50) : List<NewsCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addListNews(news: List<NewsCache>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSingleNews(news: NewsCache)

    @Query("DELETE FROM newsCache")
    suspend fun clearAllNews()

    @Delete
    suspend fun clearNews(news: NewsCache)

    @Query("SELECT COUNT(*) FROM newsCache")
    suspend fun count() : Int
}
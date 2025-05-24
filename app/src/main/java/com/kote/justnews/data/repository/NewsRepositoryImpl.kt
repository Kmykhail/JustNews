package com.kote.justnews.data.repository

import android.util.Log
import com.kote.justnews.BuildConfig
import com.kote.justnews.data.api.NewsApiService
import com.kote.justnews.data.api.NewsDto
import com.kote.justnews.domain.model.News
import com.kote.justnews.domain.repository.NewsRepository
import com.kote.justnews.data.model.Result
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl @Inject constructor(
    private val apiService: NewsApiService
): NewsRepository {
    override suspend fun getNews(category: String): Result<List<News>> {
        return try {
            val apiKey = BuildConfig.NEWS_API_KEY
            Log.d("WTF", "apiKey: $apiKey")
            val response = apiService.getTopNews(category, apiKey, "4")
            Result.Success(response.articles.mapNotNull { it.toDomain() })
//            val newsList = response.articles.mapNotNull { it.toDomain() }.sortedBy { it.publishedAt }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }
}

private fun NewsDto.toDomain(): News? {
    val id = url ?: return null
    val title = title ?: return null

    return News (
        id = id,
        title = title,
        description = description ?: "",
        url = url,
        imageUrl = urlToImage,
        author = author ?: "",
        publishedAt = Date(), // TODO, add convert
        source = News.Source(
            id = source?.id,
            name = source?.name
        )
    )
}

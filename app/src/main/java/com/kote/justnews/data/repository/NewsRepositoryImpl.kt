package com.kote.justnews.data.repository

import com.kote.justnews.data.local.LocalNewsDataSource
import com.kote.justnews.data.mapper.NewsMapper
import com.kote.justnews.domain.model.News
import com.kote.justnews.domain.repository.NewsRepository
import com.kote.justnews.data.model.Result
import com.kote.justnews.data.remote.RemoteNewsDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl @Inject constructor(
    private val remote: RemoteNewsDataSource,
    private val local: LocalNewsDataSource
): NewsRepository {
    override suspend fun getTopHeadlines(): Flow<Result<List<News>>> = flow {
        val now = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val dbUpdateTime = 3 * 60 * 60 * 1000L

        val cachedNews = local.getRecentNews()

        if (cachedNews.isNotEmpty() && now - cachedNews.first().cachedAt < dbUpdateTime) {
            emit(Result.Success(cachedNews.map { NewsMapper.newsCacheToNews(it) }))
            return@flow
        }

        local.clearAllNews()
        remote.fetchNews()
            .collect { newsList ->
                local.addListNews(newsList.map { NewsMapper.newsToCacheNews(it, now) })
                emit(Result.Success(newsList))
            }
    }.catch { e ->
        println("Error: ${e.message}")
        emit(Result.Success(emptyList()))
    }
}
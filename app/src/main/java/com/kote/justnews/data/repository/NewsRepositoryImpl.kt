package com.kote.justnews.data.repository

import com.kote.gnewsdecoder.GoogleNewsDecoder
import com.kote.justnews.data.local.LocalNewsDataSource
import com.kote.justnews.data.local.NewsCache
import com.kote.justnews.data.mapper.NewsMapper
import com.kote.justnews.domain.model.News
import com.kote.justnews.domain.repository.NewsRepository
import com.kote.justnews.data.model.Result
import com.kote.justnews.data.remote.RemoteNewsDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
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
            val shouldFetchRemote = when {
                cachedNews.isEmpty() -> true
                else -> {
                    val timeStamp = cachedNews.first().cachedAt
                    now - timeStamp > dbUpdateTime
                }
            }

            if (shouldFetchRemote) {
                local.clearAllNews()
                remote.fetchNews()
                    .map { newsList ->
                        local.addListNews(newsList.map { NewsMapper.newsToCacheNews(it, now) })
                        Result.Success(newsList) as Result<List<News>>
                    }
                    .collect { emit(it) }
            } else {
                emit(Result.Success(cachedNews.map { NewsMapper.newsCacheToNews(it) }))
            }
    }.catch { e -> emit(Result.Failure(e as Exception)) }.flowOn(Dispatchers.IO)
}
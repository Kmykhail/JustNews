package com.kote.justnews.data.local

import javax.inject.Inject

interface LocalNewsDataSource {
    suspend fun getRecentNews(): List<NewsCache>
    suspend fun addListNews(listNews: List<NewsCache>)
    suspend fun addNews(news: NewsCache)
    suspend fun isEmpty(): Boolean
    suspend fun clearAllNews()
    suspend fun clearNews(news: NewsCache)
}

class LocalNewsDataSourceImpl @Inject constructor(
    private val dao: NewsCacheDao
) : LocalNewsDataSource {
    override suspend fun getRecentNews(): List<NewsCache> = dao.getRecentNews()

    override suspend fun addListNews(listNews: List<NewsCache>) = dao.addListNews(listNews)

    override suspend fun addNews(news: NewsCache) = dao.addSingleNews(news)

    override suspend fun isEmpty() : Boolean = dao.count() == 0

    override suspend fun clearAllNews() = dao.clearAllNews()

    override suspend fun clearNews(news: NewsCache) = dao.clearNews(news)
}
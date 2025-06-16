package com.kote.justnews.data.repository

import com.kote.gnewsdecoder.GoogleNewsDecoder
import com.kote.justnews.data.local.LocalNewsDataSource
import com.kote.justnews.domain.model.News
import com.kote.justnews.domain.repository.NewsRepository
import com.kote.justnews.data.model.Result
import com.kote.justnews.data.remote.RemoteNewsDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl @Inject constructor(
    private val remote: RemoteNewsDataSource,
    private val local: LocalNewsDataSource
): NewsRepository {
    override suspend fun getTopHeadlines(): Flow<Result<List<News>>> {
        return remote.fetchNews()
            .map { newList -> Result.Success(newList) }
            .catch {e -> Result.Failure(e as Exception) }
    }

//    override suspend fun getTopHeadlines(): Result<List<News>> {
//        val date = Date()
//        return try {
//            val latestNews = getLatestNews()
//            if (latestNews.isEmpty()) {
//                val response = apiService.getTopHeadlines()
//                var news = mutableListOf<News>()
//                for (item in response.channel.items) {
//                    val result = decoder.decodeGoogleNewsUrl(item.link, 1500L)
//                    var link = ""
//                    if (result["status"] == true) {
//                        link = result["decodedUrl"] as String
//                        Log.d("ARTICLE", link)
//                    } else {
//                        Log.e("ARTICLE", "skip for title: ${item.title}, reason: ${result["message"] as String}")
//                        continue
//                    }
//                    news.add(News(
//                        id = link.hashCode().toString(),
//                        title = Jsoup.parse(item.title).text(),
//                        content = "",
//                        url = link,
//                        publishedAt = parseRssDate(item.pubDate),
//                        source = item.source?.name ?: ""
//                    ))
//                }
//                saveToCache(news)
//                Result.Success(news)
//            } else {
//                Result.Success(latestNews.map { it.toDomain() })
//            }
//        } catch (e: Exception) {
//            Result.Failure(e)
//        }
//    }
//
//    private suspend fun getLatestNews() : List<NewsCache> {
//        val cachedNews = newsDao.getRecentNews()
//        return cachedNews
//    }
//
//    private suspend fun saveToCache(news: List<News>) {
//        val newsList = news.map { item ->
//            NewsCache(
//                title = item.title,
//                description = item.content,
//                link = item.url,
//                publishedAt = item.publishedAt.time,
//                source = item.source,
//                imageUrl = item.imageUrl,
//                cachedAt = TODO(),
//            )
//        }
//        newsDao.clearAllNews()
//        newsDao.addListNews(newsList)
//    }
//}
}
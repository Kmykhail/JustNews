package com.kote.justnews.data.remote

import android.util.Log
import com.kote.justnews.BuildConfig
import com.kote.gnewsdecoder.GoogleNewsDecoder
import com.kote.justnews.data.mapper.NewsMapper
import com.kote.justnews.domain.model.News
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

interface RemoteNewsDataSource {
    suspend fun fetchNews() : Flow<List<News>>
}

class RemoteNewsDataSourceImpl @Inject constructor(
    private val rssApiService: RssApiService,
    private val gnewsApiService: GNewsApiService
) : RemoteNewsDataSource {
    private val decoder = GoogleNewsDecoder()
    private val rssMutex = Mutex()
    private var lastRssFetchTime: Long = 0
    private val apiKey = BuildConfig.GNEWS_API_KEY

    override suspend fun fetchNews(): Flow<List<News>> = channelFlow{
        val gnews = try {
            val gnewsArtircles = gnewsApiService.getTopHeadlines(apiKey)
            gnewsArtircles.articles.map { NewsMapper.gnewsArticleToNews(it) }
        } catch (e: Exception) {
            emptyList<News>().also {
                Log.e("RemoteNewsDataSourceImpl", "GNews fetch failed", e)
            }
        }

//        send(gnews)

        val rssNews = try {
            fetchRssWithLimit()
        } catch (e: Exception) {
            emptyList<News>().also {
                Log.e("RemoteNewsDataSourceImpl", "RSS fetch failed", e)
            }
        }

        send(gnews + rssNews)
    }

    private suspend fun fetchRssWithLimit(): List<News> {
//        val currentTime = System.currentTimeMillis()
//        val timeSinceLastFetch = currentTime - lastRssFetchTime
//        val requiredDelay = maxOf(0, 2000L - timeSinceLastFetch)
//
//        delay(requiredDelay)

        return try {
            val response = rssApiService.getTopHeadlines()
            response.channel.items.mapNotNull { item ->
                val result = decoder.decodeGoogleNewsUrl(item.link)
                if (result["status"] == true) {
                    println("RSSTitle-OK: ${item.title}")
                    NewsMapper.rssItemToNews(item, result["decodedUrl"] as String)
                } else {
                    println("RSSTitle-BAD: ${item}, ${result["message"]}")
                    null
                }
            }.also {
                lastRssFetchTime = System.currentTimeMillis()
            }
        } catch (e: Exception) {
            Log.e("RssFetch", "Rss fetch failed", e)
            emptyList()
        }
    }
}
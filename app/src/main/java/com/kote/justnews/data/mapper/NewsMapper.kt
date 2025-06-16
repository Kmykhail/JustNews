package com.kote.justnews.data.mapper

import android.util.Log
import com.kote.justnews.data.local.NewsCache
import com.kote.justnews.data.remote.GNewsArticle
import com.kote.justnews.data.remote.RssItem
import com.kote.justnews.domain.model.News
import org.jsoup.Jsoup
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object NewsMapper {
    fun rssItemToNews(rssItem: RssItem, decodedLink: String): News {
        return News(
            title = Jsoup.parse(rssItem.title).text(),
            description = "",
            link = decodedLink,
            imageUrl = "",
            sourceUrl = rssItem.source?.url ?: "",
            sourceName = rssItem.source?.name ?: "" ,
            publishedAt = parseRssDate(rssItem.pubDate)
        )
    }

    fun gnewsArticleToNews(gnewsArticle: GNewsArticle): News {
        return News(
            title = gnewsArticle.title,
            description = gnewsArticle.description,
            link = gnewsArticle.url,
            imageUrl = gnewsArticle.image,
            sourceUrl = gnewsArticle.source.url,
            sourceName = gnewsArticle.source.name,
            publishedAt = parseGNewsDate(gnewsArticle.publishedAt)
        )
    }

    fun newsCacheToNews(newsCache: NewsCache) : News {
        return News(
            title = newsCache.title,
            description = newsCache.description,
            link = newsCache.link,
            imageUrl = newsCache.imageUrl?: "",
            sourceUrl = newsCache.sourceUrl ?: "",
            sourceName = newsCache.sourceName ?: "" ,
            publishedAt = parseRssDate(newsCache.publishedAt)
        )
    }

    // Sun, 15 Jun 2025 13:23:00 GMT
    private fun parseRssDate(dateString: String): Date {
        val format = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US)
        return try {
            format.parse(dateString) ?: Date()
        } catch (e: ParseException) {
            Log.w("RssDateError", e.toString())
            Date()
        }
    }

    // 2025-06-15T11:59:00
    private fun parseGNewsDate(dateString: String) : Date {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
        return try {
            format.parse(dateString) ?: Date()
        } catch (e: ParseException) {
            Log.w("GNewsDateError", e.toString())
            Date()
        }
    }
}
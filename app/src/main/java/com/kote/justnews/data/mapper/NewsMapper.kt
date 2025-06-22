package com.kote.justnews.data.mapper

import com.kote.justnews.data.local.NewsCache
import com.kote.justnews.data.remote.GNewsArticle
import com.kote.justnews.data.remote.RssItem
import com.kote.justnews.domain.model.News
import org.jsoup.Jsoup
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.String
import java.time.Instant

object NewsMapper {
    fun rssItemToNews(rssItem: RssItem, decodedLink: String): News {
        return News(
            title = Jsoup.parse(rssItem.title).text(),
            description = "",
            link = decodedLink,
            imageUrl = "",
            sourceUrl = rssItem.source?.url ?: "",
            sourceName = rssItem.source?.name ?: "" ,
            publishedAt = parseRssDateTime(rssItem.pubDate)
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
            publishedAt = parseGNewsDateTime(gnewsArticle.publishedAt)
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
            publishedAt = Instant.ofEpochMilli(newsCache.publishedAt).atZone(ZoneId.systemDefault()).toLocalDateTime()
        )
    }

    fun newsToCacheNews(news: News, now: Long) : NewsCache {
        return NewsCache(
            title = news.title,
            link = news.link,
            imageUrl = news.imageUrl,
            description = news.description,
            sourceUrl = news.sourceUrl,
            sourceName = news.sourceName,
            publishedAt = news.publishedAt.toInstant(ZoneOffset.UTC).toEpochMilli(),
            cachedAt = now
        )
    }

    // Sun, 15 Jun 2025 13:23:00 GMT
    private fun parseRssDateTime(dateString: String): LocalDateTime {
        return ZonedDateTime
            .parse(dateString, DateTimeFormatter.RFC_1123_DATE_TIME)
            .toLocalDateTime()
    }

    // 2025-06-15T11:59:00Z
    private fun parseGNewsDateTime(dateString: String) : LocalDateTime {
        return Instant.parse(dateString)
            .atOffset(ZoneOffset.UTC)
            .toLocalDateTime()
    }
}
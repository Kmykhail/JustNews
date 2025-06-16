package com.kote.justnews.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface RssApiService {
    // https://news.google.com/rss?hl=uk&gl=UA&ceid=UA:uk
    @GET("rss")
    suspend fun getTopHeadlines(
        @Query("hl") language: String = "uk",
        @Query("gl") geo: String = "UA",
        @Query("ceid") ceid: String = "UA:uk"
    ) : RssResponse
}

interface GNewsApiService {
    @GET("api/v4/top-headlines/")
    suspend fun getTopHeadlines(
        @Query("apikey") apikey: String,
        @Query("language") language: String = "uk",
        @Query("country") country: String = "ua"
    ) : GNewsResponse
}

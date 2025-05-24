package com.kote.justnews.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("top-headlines")
    suspend fun getTopNews(
        @Query("category") category: String,
        @Query("apiKey") apiKey: String,
        @Query("pageSize") pageSize: String
    ) : NewsResponseDto
}
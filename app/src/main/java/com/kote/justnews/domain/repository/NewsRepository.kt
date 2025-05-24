package com.kote.justnews.domain.repository

import com.kote.justnews.data.model.Result
import com.kote.justnews.domain.model.News

interface NewsRepository {
    suspend fun getNews(category: String) : Result<List<News>>
}
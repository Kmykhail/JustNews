package com.kote.justnews.domain.repository

import com.kote.justnews.data.model.Result
import com.kote.justnews.domain.model.News
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getTopHeadlines(): Flow<Result<List<News>>>
}
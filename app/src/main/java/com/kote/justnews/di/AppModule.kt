package com.kote.justnews.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.kote.justnews.data.api.NewsApiService
import com.kote.justnews.data.repository.NewsRepositoryImpl
import com.kote.justnews.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideRetrofit() : Retrofit {
        val netJson = Json {ignoreUnknownKeys = true}
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(
                netJson.asConverterFactory("application/json".toMediaType())
            )
            .build()
    }

    @Provides
    fun provideNewsApiService(retrofit: Retrofit) : NewsApiService {
        return retrofit.create(NewsApiService::class.java)
    }

    @Provides
    fun provideNewsRepository(apiService: NewsApiService) : NewsRepository {
        return NewsRepositoryImpl(apiService)
    }
}
@file:Suppress("DEPRECATION")

package com.kote.justnews.di
import android.app.Application
import android.content.Context
import com.kote.justnews.data.local.LocalNewsDataSource
import com.kote.justnews.data.local.LocalNewsDataSourceImpl
import com.kote.justnews.data.remote.RssApiService
import com.kote.justnews.data.local.NewsCacheDao
import com.kote.justnews.data.local.NewsCacheDatabase
import com.kote.justnews.data.remote.GNewsApiService
import com.kote.justnews.data.remote.RemoteNewsDataSource
import com.kote.justnews.data.remote.RemoteNewsDataSourceImpl
import com.kote.justnews.data.repository.NewsRepositoryImpl
import com.kote.justnews.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideDatabase(app: Application) : NewsCacheDatabase {
        return NewsCacheDatabase.getDataBase(app.applicationContext)
    }

    @Provides
    fun provideDao(database: NewsCacheDatabase) : NewsCacheDao {
        return database.dao()
    }

    @Provides
    @Named("googleNewsRetrofit")
    fun provideGoogleNewsRetrofit() : Retrofit {
        val xmlConverterFactory = SimpleXmlConverterFactory.createNonStrict(
            Persister(AnnotationStrategy())
        )
        return Retrofit.Builder()
            .baseUrl("https://news.google.com/")
            .addConverterFactory(xmlConverterFactory)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BASIC
                    })
                    .build()
            )
            .build()
    }

    @Provides
    @Named("gnewsRetrofit")
    fun provideGNewsRetrofit() : Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://gnews.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BASIC
                    })
                    .build()
            )
            .build()
    }

    @Provides
    fun provideGoogleNewsApiService(
        @Named("googleNewsRetrofit")retrofit: Retrofit
    ) : RssApiService {
        return retrofit.create(RssApiService::class.java)
    }

    @Provides
    fun provideGNewsApiService(
        @Named("gnewsRetrofit")retrofit: Retrofit
    ) : GNewsApiService {
        return retrofit.create(GNewsApiService::class.java)
    }

    @Provides fun provideRemote(ds: RemoteNewsDataSourceImpl): RemoteNewsDataSource = ds
    @Provides fun provideLocal(ds: LocalNewsDataSourceImpl): LocalNewsDataSource = ds

    @Provides
    fun provideNewsRepository(remote: RemoteNewsDataSource, local : LocalNewsDataSource) : NewsRepository {
        return NewsRepositoryImpl(remote, local)
    }

    @Provides
    fun provideContext(app: Application): Context {
        return app.applicationContext
    }
}

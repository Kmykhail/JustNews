package com.kote.justnews.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NewsCache::class], version = 1, exportSchema = false)
abstract class NewsCacheDatabase: RoomDatabase() {
    abstract fun dao(): NewsCacheDao

    companion object {
        private var INSTANCE: NewsCacheDatabase? = null
        fun getDataBase(context: Context): NewsCacheDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context, NewsCacheDatabase::class.java, "news_cache_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
package com.suki.app.di

import android.content.Context
import androidx.room.Room
import com.suki.app.data.MoodDatabase
import com.suki.app.data.MoodDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideMoodDatabase(@ApplicationContext context: Context): MoodDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MoodDatabase::class.java,
            "mood_database"
        ).build()
    }
    
    @Provides
    fun provideMoodDao(database: MoodDatabase): MoodDao {
        return database.moodDao()
    }
}

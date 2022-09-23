package com.bogdanov.di

import android.content.Context
import com.bogdanov.data.local.EventsLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): com.bogdanov.data.local.EventsDatabase =
        com.bogdanov.data.local.EventsDatabase.create(context)

    @Provides
    fun provideDao(database: com.bogdanov.data.local.EventsDatabase): com.bogdanov.data.local.EventDao =
        database.eventsDao()

    @Singleton
    @Provides
    fun providesEventsLocalDataSource(dao: com.bogdanov.data.local.EventDao) =
        EventsLocalDataSource(eventDao = dao)

}
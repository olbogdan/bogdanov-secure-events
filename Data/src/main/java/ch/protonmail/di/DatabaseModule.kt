package ch.protonmail.di

import android.content.Context
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
    fun provideDatabase(@ApplicationContext context: Context): ch.protonmail.data.local.TasksDatabase =
        ch.protonmail.data.local.TasksDatabase.create(context)

    @Provides
    fun provideDao(database: ch.protonmail.data.local.TasksDatabase): ch.protonmail.data.local.TasksDao =
        database.tasksDao()

    @Singleton
    @Provides
    fun providesTasksLocalDataSource(dao: ch.protonmail.data.local.TasksDao) =
        ch.protonmail.data.local.TasksLocalDataSource(tasksDao = dao)

}
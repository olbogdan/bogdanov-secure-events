package ch.protonmail.android.protonmailtest.di

import android.content.Context
import ch.protonmail.android.protonmailtest.data.local.TasksDao
import ch.protonmail.android.protonmailtest.data.local.TasksDatabase
import ch.protonmail.android.protonmailtest.data.local.TasksLocalDataSource
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
    fun provideDatabase(@ApplicationContext context: Context): TasksDatabase = TasksDatabase.create(context)

    @Provides
    fun provideDao(database: TasksDatabase): TasksDao = database.tasksDao()

    @Singleton
    @Provides
    fun providesTasksLocalDataSource(dao: TasksDao) = TasksLocalDataSource(tasksDao = dao)

}
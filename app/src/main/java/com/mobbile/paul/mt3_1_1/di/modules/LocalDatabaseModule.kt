package com.example.kotlin_project.di.modules

import android.app.Application
import androidx.room.Room
import com.example.kotlin_project.providers.AppDao
import com.example.kotlin_project.providers.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalDatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideRecipeDao(db: AppDatabase): AppDao {
        return db.appdao
    }
}
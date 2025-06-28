package com.peterdanh.githubuserbrowser.di

import android.app.Application
import androidx.room.Room
import com.peterdanh.githubuserbrowser.data.local.dao.UserDao
import com.peterdanh.githubuserbrowser.data.local.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module that provides database-related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Provides a singleton instance of [AppDatabase] using Room.
     *
     * @param app The application context.
     * @return The [AppDatabase] instance.
     */
    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
                app,
                AppDatabase::class.java,
                "github_users.db"
            ).fallbackToDestructiveMigration(false).build()
    }

    /**
     * Provides the [UserDao] for accessing user data in the database.
     *
     * @param db The [AppDatabase] instance.
     * @return The [UserDao] instance.
     */
    @Provides
    fun provideUserDao(db: AppDatabase): UserDao {
        return db.userDao()
    }
}

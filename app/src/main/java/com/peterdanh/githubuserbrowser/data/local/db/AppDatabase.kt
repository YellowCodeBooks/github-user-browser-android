package com.peterdanh.githubuserbrowser.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.peterdanh.githubuserbrowser.data.local.dao.UserDao
import com.peterdanh.githubuserbrowser.data.local.entity.UserEntity

/**
 * The Room database for the application, containing the user table.
 *
 * @property userDao Provides access to user-related database operations.
 */
@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Returns the [UserDao] for performing user database operations.
     */
    abstract fun userDao(): UserDao
}
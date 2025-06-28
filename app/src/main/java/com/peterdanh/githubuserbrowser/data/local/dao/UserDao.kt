package com.peterdanh.githubuserbrowser.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.peterdanh.githubuserbrowser.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for user-related database operations.
 */
@Dao
interface UserDao {

    /**
     * Retrieves all users from the database as a stream.
     *
     * @return A [Flow] emitting the list of all [UserEntity] objects.
     */
    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<UserEntity>>

    /**
     * Inserts a list of users into the database.
     * Replaces existing users on conflict.
     *
     * @param users The list of [UserEntity] objects to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)

    /**
     * Deletes all users from the database.
     */
    @Query("DELETE FROM users")
    suspend fun clearUsers()
}

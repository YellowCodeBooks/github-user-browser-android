package com.peterdanh.githubuserbrowser.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a user entity stored in the local database.
 *
 * @property id The unique identifier of the user (primary key).
 * @property login The unique username of the user.
 * @property avatarUrl The URL of the user's avatar image.
 * @property htmlUrl The URL of the user's GitHub profile.
 */
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Long,
    val login: String,
    val avatarUrl: String,
    val htmlUrl: String
)

package com.peterdanh.githubuserbrowser.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val login: String,
    val avatarUrl: String,
    val htmlUrl: String
)

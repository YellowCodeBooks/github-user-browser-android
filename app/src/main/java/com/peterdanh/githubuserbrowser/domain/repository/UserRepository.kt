package com.peterdanh.githubuserbrowser.domain.repository

import com.peterdanh.githubuserbrowser.domain.model.User
import com.peterdanh.githubuserbrowser.domain.model.UserDetail
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(since: Int): Flow<List<User>>
    suspend fun getUserDetail(username: String): UserDetail
}
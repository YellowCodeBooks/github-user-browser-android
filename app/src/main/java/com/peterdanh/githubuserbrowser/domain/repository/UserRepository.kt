package com.peterdanh.githubuserbrowser.domain.repository

import com.peterdanh.githubuserbrowser.domain.model.UserDetail
import com.peterdanh.githubuserbrowser.domain.model.UserResult
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(since: Int): Flow<UserResult>
    suspend fun getUserDetail(username: String): UserDetail
}
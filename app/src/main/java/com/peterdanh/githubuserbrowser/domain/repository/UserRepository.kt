package com.peterdanh.githubuserbrowser.domain.repository

import com.peterdanh.githubuserbrowser.domain.model.User
import com.peterdanh.githubuserbrowser.domain.model.UserDetail

interface UserRepository {
    suspend fun getUsers(since: Int): List<User>
    suspend fun getUserDetail(username: String): UserDetail
}
package com.peterdanh.githubuserbrowser.domain.repository

import com.peterdanh.githubuserbrowser.domain.model.User

interface UserRepository {
    suspend fun getUsers(since: Int): List<User>
}
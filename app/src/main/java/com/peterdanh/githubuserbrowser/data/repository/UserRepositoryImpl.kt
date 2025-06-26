package com.peterdanh.githubuserbrowser.data.repository

import com.peterdanh.githubuserbrowser.data.remote.GitHubApiService
import com.peterdanh.githubuserbrowser.domain.model.User
import com.peterdanh.githubuserbrowser.domain.model.UserDetail
import com.peterdanh.githubuserbrowser.domain.repository.UserRepository

class UserRepositoryImpl(
    private val api: GitHubApiService
) : UserRepository {
    override suspend fun getUsers(since: Int): List<User> {
        return api.getUsers(since).map { it.toDomain() }
    }

    override suspend fun getUserDetail(username: String): UserDetail {
        return api.getUserDetail(username).toDomain()
    }
}
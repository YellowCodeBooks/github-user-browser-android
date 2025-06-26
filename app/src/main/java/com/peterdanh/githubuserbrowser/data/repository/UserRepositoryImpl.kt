package com.peterdanh.githubuserbrowser.data.repository

import com.peterdanh.githubuserbrowser.data.local.dao.UserDao
import com.peterdanh.githubuserbrowser.data.mapper.toDomain
import com.peterdanh.githubuserbrowser.data.mapper.toEntity
import com.peterdanh.githubuserbrowser.data.remote.GitHubApiService
import com.peterdanh.githubuserbrowser.domain.model.UserDetail
import com.peterdanh.githubuserbrowser.domain.model.UserResult
import com.peterdanh.githubuserbrowser.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val api: GitHubApiService,
    private val userDao: UserDao
) : UserRepository {
    override fun getUsers(since: Int): Flow<UserResult> = flow {
        var apiUserCount = 0
        try {
            val remoteUsers = api.getUsers(since).map { it.toDomain().toEntity() }
            userDao.insertUsers(remoteUsers)
            apiUserCount = remoteUsers.size
        } catch (_: Exception) {
            // ignore error → load from local anyway
        }

        emitAll(
            userDao.getAllUsers().map { list ->
                UserResult(
                    users = list.map { it.toDomain() },
                    apiUserCount = apiUserCount
                )
            }
        )
    }

    override suspend fun getUserDetail(username: String): UserDetail {
        return api.getUserDetail(username).toDomain()
    }
}
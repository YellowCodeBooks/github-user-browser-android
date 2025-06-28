package com.peterdanh.githubuserbrowser.data.repository

import android.util.Log
import com.peterdanh.githubuserbrowser.data.local.dao.UserDao
import com.peterdanh.githubuserbrowser.data.mapper.toDomain
import com.peterdanh.githubuserbrowser.data.mapper.toEntity
import com.peterdanh.githubuserbrowser.data.remote.GitHubApiService
import com.peterdanh.githubuserbrowser.domain.model.User
import com.peterdanh.githubuserbrowser.domain.model.UserDetail
import com.peterdanh.githubuserbrowser.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val api: GitHubApiService,
    private val userDao: UserDao
) : UserRepository {
    override fun getUsers(since: Int): Flow<List<User>> = flow {
        var apiUserCount = 0
        try {
            val remoteUsers = api.getUsers(since).map {
                it.toDomain().toEntity()
            }
            userDao.insertUsers(remoteUsers)
            apiUserCount = remoteUsers.size
        } catch (e: Exception) {
            Log.d("UserRepositoryImpl", "Error fetching users from API, loading from local, exception: ${e.message}")
            // API failed, will check local data below
            val localUsers = userDao.getAllUsers().map { list -> list.map { it.toDomain() } }.first()
            if (localUsers.isEmpty()) {
                throw Exception("Error fetching users from API and no local data: ${e.message}")
            }
            emit(localUsers)
            return@flow
        }

        emitAll(
            userDao.getAllUsers().map { list ->
                list.map { it.toDomain() }
            }
        )
    }

    override suspend fun getUserDetail(username: String): UserDetail {
        return api.getUserDetail(username).toDomain()
    }
}
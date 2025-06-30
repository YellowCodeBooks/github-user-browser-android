package com.peterdanh.githubuserbrowser.data.repository

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

/**
 * Implementation of [UserRepository] that manages user data from both remote (GitHub API)
 * and local (Room database) sources.
 *
 * @property api The [GitHubApiService] for remote data access.
 * @property userDao The [UserDao] for local database operations.
 */
class UserRepositoryImpl(
    private val api: GitHubApiService,
    private val userDao: UserDao
) : UserRepository {

    /**
     * Retrieves a list of users starting from the specified user index.
     * Attempts to fetch from the API and caches results locally.
     * Falls back to local data if the API call fails.
     *
     * @param since The user index to start the list from (for pagination).
     * @return A [Flow] emitting lists of [User] objects.
     * @throws Exception if both remote and local data sources fail.
     */
    override fun getUsers(since: Int): Flow<List<User>> = flow {
        try {
            val remoteUsers = api.getUsers(since).map {
                it.toDomain().toEntity()
            }
            userDao.insertUsers(remoteUsers)
        } catch (e: Exception) {
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

    /**
     * Retrieves detailed information for a specific user from the remote API.
     *
     * @param username The username of the user to retrieve details for.
     * @return A [UserDetail] object containing detailed user information.
     */
    override suspend fun getUserDetail(username: String): UserDetail {
        return api.getUserDetail(username).toDomain()
    }
}
package com.peterdanh.githubuserbrowser.domain.repository

import com.peterdanh.githubuserbrowser.domain.model.User
import com.peterdanh.githubuserbrowser.domain.model.UserDetail
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for accessing GitHub user data.
 */
interface UserRepository {

    /**
     * Retrieves a list of users starting from the specified user index.
     *
     * @param since The user index to start the list from (for pagination).
     * @return A [Flow] emitting lists of [User] objects.
     */
    fun getUsers(since: Int): Flow<List<User>>

    /**
     * Retrieves detailed information for a specific user.
     *
     * @param username The username of the user to retrieve details for.
     * @return A [UserDetail] object containing detailed user information.
     */
    suspend fun getUserDetail(username: String): UserDetail
}
package com.peterdanh.githubuserbrowser.domain.usecase

import com.peterdanh.githubuserbrowser.domain.model.UserDetail
import com.peterdanh.githubuserbrowser.domain.repository.UserRepository

/**
 * Use case for retrieving detailed information about a specific GitHub user.
 *
 * @property repository The [UserRepository] used to fetch user details.
 */
class GetUserDetailUseCase(
    private val repository: UserRepository
) {
    /**
     * Invokes the use case to get detailed information for the given username.
     *
     * @param username The username of the user to retrieve details for.
     * @return A [UserDetail] object containing detailed user information.
     */
    suspend operator fun invoke(username: String): UserDetail {
        return repository.getUserDetail(username)
    }
}
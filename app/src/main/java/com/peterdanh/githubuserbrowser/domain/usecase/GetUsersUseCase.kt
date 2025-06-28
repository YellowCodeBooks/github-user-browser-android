package com.peterdanh.githubuserbrowser.domain.usecase

import com.peterdanh.githubuserbrowser.domain.model.User
import com.peterdanh.githubuserbrowser.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for retrieving a list of GitHub users starting from a specific user index.
 *
 * @property repository The [UserRepository] used to fetch user data.
 */
class GetUsersUseCase @Inject constructor(
    private val repository: UserRepository
) {
    /**
     * Invokes the use case to get a list of users starting from the given user index.
     *
     * @param since The user index to start the list from (for pagination).
     * @return A [Flow] emitting lists of [User] objects.
     */
    operator fun invoke(since: Int): Flow<List<User>> {
        return repository.getUsers(since)
    }
}
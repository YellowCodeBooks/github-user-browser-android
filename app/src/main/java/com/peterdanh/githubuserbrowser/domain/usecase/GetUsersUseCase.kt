package com.peterdanh.githubuserbrowser.domain.usecase

import com.peterdanh.githubuserbrowser.domain.model.UserResult
import com.peterdanh.githubuserbrowser.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(since: Int): Flow<UserResult> {
        return repository.getUsers(since)
    }
}
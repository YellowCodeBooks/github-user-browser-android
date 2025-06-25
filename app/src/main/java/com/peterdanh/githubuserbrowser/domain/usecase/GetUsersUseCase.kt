package com.peterdanh.githubuserbrowser.domain.usecase

import com.peterdanh.githubuserbrowser.domain.model.User
import com.peterdanh.githubuserbrowser.domain.repository.UserRepository

class GetUsersUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(since: Int): List<User> {
        return repository.getUsers(since)
    }
}
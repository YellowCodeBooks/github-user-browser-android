package com.peterdanh.githubuserbrowser.domain.usecase

import com.peterdanh.githubuserbrowser.domain.model.UserDetail
import com.peterdanh.githubuserbrowser.domain.repository.UserRepository

class GetUserDetailUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(username: String): UserDetail {
        return repository.getUserDetail(username)
    }
}
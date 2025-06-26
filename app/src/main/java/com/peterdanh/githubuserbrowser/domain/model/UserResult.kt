package com.peterdanh.githubuserbrowser.domain.model

data class UserResult(
    val users: List<User>,
    val apiUserCount: Int = 0
)

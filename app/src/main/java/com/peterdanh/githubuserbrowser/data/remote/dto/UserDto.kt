package com.peterdanh.githubuserbrowser.data.remote.dto

import com.peterdanh.githubuserbrowser.domain.model.User

data class UserDto(
    val login: String,
    val avatar_url: String,
    val html_url: String
) {
    fun toDomain(): User = User(
        login = login,
        avatarUrl = avatar_url,
        htmlUrl = html_url
    )
}

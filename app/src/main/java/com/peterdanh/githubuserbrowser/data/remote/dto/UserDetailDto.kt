package com.peterdanh.githubuserbrowser.data.remote.dto

import com.peterdanh.githubuserbrowser.domain.model.UserDetail

data class UserDetailDto(
    val login: String,
    val avatar_url: String,
    val html_url: String,
    val location: String?,
    val followers: Int,
    val following: Int
) {
    fun toDomain(): UserDetail = UserDetail(
        login = login,
        avatarUrl = avatar_url,
        htmlUrl = html_url,
        location = location,
        followers = followers,
        following = following
    )
}

package com.peterdanh.githubuserbrowser.data.remote.dto

data class UserDetailDto(
    val login: String,
    val avatar_url: String,
    val html_url: String,
    val location: String?,
    val followers: Int,
    val following: Int
)

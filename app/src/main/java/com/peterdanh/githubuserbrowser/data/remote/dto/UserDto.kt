package com.peterdanh.githubuserbrowser.data.remote.dto

/**
 * Data Transfer Object representing a GitHub user as received from the remote API.
 *
 * @property login The username of the user.
 * @property avatar_url The URL of the user's avatar image.
 * @property html_url The URL to the user's GitHub profile page.
 */
data class UserDto(
    val id: Long,
    val login: String,
    val avatar_url: String,
    val html_url: String
)

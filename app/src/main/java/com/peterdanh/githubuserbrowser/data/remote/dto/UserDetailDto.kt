package com.peterdanh.githubuserbrowser.data.remote.dto

/**
 * Data Transfer Object representing detailed information about a GitHub user
 * as received from the remote API.
 *
 * @property login The username of the user.
 * @property avatar_url The URL of the user's avatar image.
 * @property html_url The URL to the user's GitHub profile page.
 * @property location The location of the user, or null if not specified.
 * @property followers The number of followers the user has.
 * @property following The number of users the user is following.
 */
data class UserDetailDto(
    val login: String,
    val avatar_url: String,
    val html_url: String,
    val location: String?,
    val followers: Int,
    val following: Int
)

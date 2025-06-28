package com.peterdanh.githubuserbrowser.domain.model

/**
 * Represents detailed information about a GitHub user.
 *
 * @property login The username of the user.
 * @property avatarUrl The URL of the user's avatar image.
 * @property htmlUrl The URL to the user's GitHub profile page.
 * @property location The location of the user, or null if not specified.
 * @property followers The number of followers the user has.
 * @property following The number of users the user is following.
 */
data class UserDetail(
    val login: String,
    val avatarUrl: String,
    val htmlUrl: String,
    val location: String?,
    val followers: Int,
    val following: Int
)

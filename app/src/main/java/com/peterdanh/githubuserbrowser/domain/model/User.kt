package com.peterdanh.githubuserbrowser.domain.model

/**
 * Represents a GitHub user with basic profile information.
 *
 * @property id The unique identifier of the user.
 * @property login The username of the user.
 * @property avatarUrl The URL of the user's avatar image.
 * @property htmlUrl The URL to the user's GitHub profile page.
 */
data class User(
    val id: Long,
    val login: String,
    val avatarUrl: String,
    val htmlUrl: String
)

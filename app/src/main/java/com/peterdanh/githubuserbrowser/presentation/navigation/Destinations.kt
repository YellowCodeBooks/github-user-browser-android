package com.peterdanh.githubuserbrowser.presentation.navigation

import kotlinx.serialization.Serializable

/**
 * Represents the Home destination in the navigation graph.
 */
@Serializable
object Home

/**
 * Represents the Detail destination, which shows details for a specific user.
 *
 * @property username The username of the user whose details are displayed.
 */
@Serializable
data class Detail(val username: String)
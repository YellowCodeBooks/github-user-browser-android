package com.peterdanh.githubuserbrowser.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object HomeScreen

@Serializable
data class DetailScreen(val username: String)
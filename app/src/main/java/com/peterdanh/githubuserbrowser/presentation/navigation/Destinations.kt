package com.peterdanh.githubuserbrowser.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
data class Detail(val username: String)
package com.peterdanh.githubuserbrowser

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class for the GitHub User Browser app.
 *
 * Annotated with [HiltAndroidApp] to trigger Hilt's code generation and enable dependency injection.
 */
@HiltAndroidApp
class GitHubUserBrowserApp : Application()
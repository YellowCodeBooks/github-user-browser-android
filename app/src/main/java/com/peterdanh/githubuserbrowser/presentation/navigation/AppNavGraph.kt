package com.peterdanh.githubuserbrowser.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.peterdanh.githubuserbrowser.presentation.screen.detail.DetailScreen
import com.peterdanh.githubuserbrowser.presentation.screen.home.HomeScreen

/**
 * Sets up the navigation graph for the app using Jetpack Compose Navigation.
 *
 * @param modifier Modifier to be applied to the NavHost.
 * @param navController Controller that handles navigation within the app.
 */
@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Home
    ) {
        composable<Home> {
            HomeScreen(
                onNavigateToDetail = { username ->
                    navController.navigate(Detail(username))
                }
            )
        }

        composable<Detail> { backStackEntry ->
            val detailScreenRoute: Detail = backStackEntry.toRoute()
            DetailScreen(
                username = detailScreenRoute.username,
                navController = navController
            )
        }
    }
}
package com.peterdanh.githubuserbrowser.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.peterdanh.githubuserbrowser.presentation.screen.home.HomeScreen

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HomeScreen
    ) {
        composable<HomeScreen> {
            HomeScreen(
                onNavigateToDetail = { username ->
                    navController.navigate(DetailScreen(username))
                }
            )
        }

        composable<DetailScreen> { backStackEntry ->
            val detailScreenRoute: DetailScreen = backStackEntry.toRoute()
            DetailScreen(username = detailScreenRoute.username)
        }
    }
}
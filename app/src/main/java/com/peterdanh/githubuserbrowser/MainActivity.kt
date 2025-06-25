package com.peterdanh.githubuserbrowser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph
import androidx.navigation.compose.rememberNavController
import com.peterdanh.githubuserbrowser.presentation.navigation.AppNavGraph
import com.peterdanh.githubuserbrowser.presentation.theme.GitHubUserBrowserAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GitHubUserBrowserAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppContent(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun AppContent(modifier: Modifier = Modifier) {
    val naController = rememberNavController()
    AppNavGraph(
        modifier = modifier,
        navController = naController
    )
}

@Preview(showBackground = true)
@Composable
fun AppContentPreview() {
    GitHubUserBrowserAndroidTheme {
        AppContent()
    }
}
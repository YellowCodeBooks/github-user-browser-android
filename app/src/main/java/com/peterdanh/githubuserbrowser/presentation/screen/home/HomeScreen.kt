package com.peterdanh.githubuserbrowser.presentation.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.peterdanh.githubuserbrowser.presentation.theme.GitHubUserBrowserAndroidTheme

@Composable
fun HomeScreen(onNavigateToDetail: (String) -> Unit) {
    val fakeUsers = listOf("octocat", "torvalds")

    LazyColumn {
        items(fakeUsers) { username ->
            Text(
                text = username,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigateToDetail(username) }
                    .padding(16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    GitHubUserBrowserAndroidTheme {
        HomeScreen(onNavigateToDetail = {})
    }
}
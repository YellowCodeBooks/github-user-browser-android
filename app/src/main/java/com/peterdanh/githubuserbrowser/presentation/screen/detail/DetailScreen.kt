package com.peterdanh.githubuserbrowser.presentation.screen.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.peterdanh.githubuserbrowser.presentation.theme.GitHubUserBrowserAndroidTheme
import com.peterdanh.githubuserbrowser.presentation.viewmodel.DetailViewModel
import androidx.compose.runtime.getValue
import coil.compose.AsyncImage
import com.peterdanh.githubuserbrowser.presentation.component.FollowerStat
import com.peterdanh.githubuserbrowser.presentation.component.UserCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    username: String,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val user by viewModel.userDetail.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(username) {
        viewModel.loadUserDetail(username)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = username) })
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                error != null -> {
                    Text(
                        text = error ?: "",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                user != null -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        UserCard(
                            avatarUrl = user!!.avatarUrl,
                            name = user!!.login,
                            location = user!!.location
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            FollowerStat(Icons.Default.Person, "Follower", "${user!!.followers}+")
                            FollowerStat(Icons.Default.Person, "Following", "${user!!.following}+")
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        Text("Blog", style = MaterialTheme.typography.titleMedium)
                        Text(text = user!!.htmlUrl, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDetailScreen() {
    GitHubUserBrowserAndroidTheme {
        DetailScreen(username = "octocat")
    }
}
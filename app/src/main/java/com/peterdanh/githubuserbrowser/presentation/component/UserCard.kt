package com.peterdanh.githubuserbrowser.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.peterdanh.githubuserbrowser.R
import com.peterdanh.githubuserbrowser.presentation.theme.GitHubUserBrowserAndroidTheme

/**
 * Displays a user card with avatar, name, and custom subtitle content.
 *
 * @param avatarUrl The URL of the user's avatar image.
 * @param name The name of the user to display.
 * @param modifier The [Modifier] to be applied to the card.
 * @param subtitleContent A composable lambda for displaying additional content below the name.
 */
@Composable
fun UserCard(
    avatarUrl: String,
    name: String,
    modifier: Modifier = Modifier,
    subtitleContent: @Composable () -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Color.White,
        shadowElevation = 4.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .padding(16.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(78.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Gray.copy(alpha = 0.1f))
                    .padding(4.dp)
            ) {
                AsyncImage(
                    model = avatarUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape),
                    placeholder = painterResource(id = R.drawable.ic_avatar_placeholder),
                    error = painterResource(id = R.drawable.ic_avatar_placeholder)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = name, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(8.dp))
                subtitleContent()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserCard() {
    GitHubUserBrowserAndroidTheme {
        UserCard(
            avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4", name = "David Patel"
        ) {
            Text("Sub content")
        }
    }
}
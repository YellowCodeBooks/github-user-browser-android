package com.peterdanh.githubuserbrowser.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.peterdanh.githubuserbrowser.R
import com.peterdanh.githubuserbrowser.presentation.theme.GitHubUserBrowserAndroidTheme

/**
 * Displays a follower statistic with an icon, count, and label.
 *
 * @param icon Resource ID of the icon to display.
 * @param label The label describing the statistic (e.g., "Follower").
 * @param count The count value to display.
 */
@Composable
fun FollowerStat(
    @DrawableRes icon: Int,
    label: String,
    count: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .background(
                    color = Color.Gray.copy(alpha = 0.1f),
                    shape = CircleShape
                )
                .padding(8.dp)
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
        }
        Text(text = count, style = MaterialTheme.typography.titleMedium)
        Text(text = label, style = MaterialTheme.typography.bodySmall)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFollowerStatRow() {
    GitHubUserBrowserAndroidTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            FollowerStat(
                icon = R.drawable.ic_follower,
                label = "Follower",
                count = "100+"
            )
            FollowerStat(
                icon = R.drawable.ic_following,
                label = "Following",
                count = "10+"
            )
        }
    }
}
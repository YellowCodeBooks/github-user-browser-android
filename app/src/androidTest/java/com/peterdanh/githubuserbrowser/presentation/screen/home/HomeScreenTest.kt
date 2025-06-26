package com.peterdanh.githubuserbrowser.presentation.screen.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.peterdanh.githubuserbrowser.presentation.MainActivity
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun homeScreen_showsLoadingThenUsers() {
        composeTestRule.mainClock.autoAdvance = true
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule
                .onAllNodesWithText("GitHub Users")
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("GitHub Users").assertIsDisplayed()

        composeTestRule
            .onAllNodes(hasText("https://github.com", substring = true))
            .onFirst()
            .assertIsDisplayed()
    }

    @Test
    fun clickingUser_navigatesToDetailScreen() {
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule
                .onAllNodes(hasText("https://github.com", substring = true))
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule
            .onAllNodes(hasText("https://github.com", substring = true))
            .onFirst()
            .performClick()

        composeTestRule.waitUntil(timeoutMillis = 3000) {
            composeTestRule
                .onAllNodes(hasText("Follower", substring = true))
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Follower").assertIsDisplayed()
    }
}
package com.peterdanh.githubuserbrowser.presentation.screen.detail

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.peterdanh.githubuserbrowser.presentation.MainActivity
import org.junit.Rule
import org.junit.Test

class DetailScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun detailScreen_displaysUserDetailsCorrectly() {
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule
                .onAllNodes(hasText("https://github.com", substring = true))
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule
            .onAllNodes(hasText("https://github.com", substring = true))
            .onFirst()
            .performClick()

        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule
                .onAllNodes(hasText("Follower", substring = true))
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule
            .onAllNodes(hasText("https://github.com", substring = true))
            .onFirst()
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Follower", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Following", substring = true).assertIsDisplayed()
    }
}
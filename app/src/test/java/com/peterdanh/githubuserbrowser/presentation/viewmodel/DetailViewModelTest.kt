package com.peterdanh.githubuserbrowser.presentation.viewmodel

import com.peterdanh.githubuserbrowser.domain.model.UserDetail
import com.peterdanh.githubuserbrowser.domain.usecase.GetUserDetailUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: DetailViewModel
    private lateinit var getUserDetailUseCase: GetUserDetailUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getUserDetailUseCase = mockk()
        viewModel = DetailViewModel(getUserDetailUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadUserDetail should populate userDetail on success`() = runTest {
        val user = UserDetail(
            login = "david",
            avatarUrl = "https://avatar.com/david",
            htmlUrl = "https://github.com/david",
            location = "Vietnam",
            followers = 123,
            following = 45
        )

        coEvery { getUserDetailUseCase("david") } returns user

        viewModel.loadUserDetail("david")
        advanceUntilIdle()

        assertEquals(false, viewModel.isLoading.value)
        assertNull(viewModel.error.value)
        assertNotNull(viewModel.userDetail.value)
        assertEquals("david", viewModel.userDetail.value?.login)
    }

    @Test
    fun `loadUserDetail should emit error on failure`() = runTest {
        coEvery { getUserDetailUseCase("errorUser") } throws RuntimeException("User not found")

        viewModel.loadUserDetail("errorUser")
        advanceUntilIdle()

        assertEquals(false, viewModel.isLoading.value)
        assertEquals("User not found", viewModel.error.value)
        assertNull(viewModel.userDetail.value)
    }
}
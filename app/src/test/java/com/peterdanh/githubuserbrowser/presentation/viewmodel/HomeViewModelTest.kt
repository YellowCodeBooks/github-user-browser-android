package com.peterdanh.githubuserbrowser.presentation.viewmodel

import com.peterdanh.githubuserbrowser.domain.model.User
import com.peterdanh.githubuserbrowser.domain.usecase.GetUsersUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
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
class HomeViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: HomeViewModel
    private lateinit var getUsersUseCase: GetUsersUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getUsersUseCase = mockk()
        viewModel = HomeViewModel(getUsersUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadUsers should update users list and stop loading`() = runTest {
        val mockUsers = listOf(
            User("david", "https://avatar.com/1", "https://github.com/david"),
            User("lisa", "https://avatar.com/2", "https://github.com/lisa")
        )
        coEvery { getUsersUseCase(any()) } returns mockUsers

        viewModel.loadUsers()
        advanceUntilIdle()

        assertEquals(false, viewModel.isLoading.value)
        assertEquals(2, viewModel.users.value.size)
        assertEquals("david", viewModel.users.value.first().login)
    }

    @Test
    fun `loadUsers should emit error when exception occurs`() = runTest {
        coEvery { getUsersUseCase(any()) } throws RuntimeException("API failed")

        viewModel.loadUsers()
        advanceUntilIdle()

        assertEquals("Error: API failed", viewModel.error.value)
        assertEquals(false, viewModel.isLoading.value)
        assertEquals(0, viewModel.users.value.size)
    }
}
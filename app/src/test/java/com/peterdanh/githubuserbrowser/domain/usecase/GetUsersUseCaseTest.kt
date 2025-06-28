package com.peterdanh.githubuserbrowser.domain.usecase

import com.peterdanh.githubuserbrowser.domain.model.User
import com.peterdanh.githubuserbrowser.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetUsersUseCaseTest {
    private lateinit var repository: UserRepository
    private lateinit var useCase: GetUsersUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetUsersUseCase(repository)
    }

    @Test
    fun `invoke returns list of users from repository`() = runTest {
        val mockUsers = listOf(
            User("david", "https://avatar.com/1", "https://github.com/david"),
            User("lisa", "https://avatar.com/2", "https://github.com/lisa")
        )

        coEvery { repository.getUsers(any()) } returns flowOf(mockUsers)

        assertEquals(2, mockUsers.size)
        assertEquals("david", mockUsers.first().login)
    }
}
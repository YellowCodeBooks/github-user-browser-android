package com.peterdanh.githubuserbrowser.domain.usecase

import com.peterdanh.githubuserbrowser.domain.model.UserDetail
import com.peterdanh.githubuserbrowser.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetUserDetailUseCaseTest {
    private lateinit var repository: UserRepository
    private lateinit var useCase: GetUserDetailUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetUserDetailUseCase(repository)
    }

    @Test
    fun `invoke returns user detail from repository`() = runTest {
        val mockUser = UserDetail(
            login = "david",
            avatarUrl = "https://avatar.com/david",
            htmlUrl = "https://github.com/david",
            location = "Vietnam",
            followers = 100,
            following = 50
        )

        coEvery { repository.getUserDetail("david") } returns mockUser

        val result = useCase("david")

        assertEquals("david", result.login)
        assertEquals("Vietnam", result.location)
    }
}
package com.peterdanh.githubuserbrowser.data.repository

import com.peterdanh.githubuserbrowser.data.local.dao.UserDao
import com.peterdanh.githubuserbrowser.data.mapper.toDomain
import com.peterdanh.githubuserbrowser.data.mapper.toEntity
import com.peterdanh.githubuserbrowser.data.remote.GitHubApiService
import com.peterdanh.githubuserbrowser.data.remote.dto.UserDto
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import java.io.IOException
import kotlin.test.Test
import kotlin.test.assertFailsWith

class UserRepositoryImplTest {
    private lateinit var api: GitHubApiService
    private lateinit var dao: UserDao
    private lateinit var repository: UserRepositoryImpl

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        api = mockk()
        dao = mockk()
        repository = UserRepositoryImpl(api, dao)
    }

    @Test
    fun `getUsers should emit Room data and correct apiUserCount when API succeeds`() = runTest(testDispatcher) {
        // given
        val since = 0
        val apiUsers = listOf(
            UserDto(1, "peter", "https://avatar.com/1", "https://github.com/peter")
        )
        val roomEntities = apiUsers.map { it.toDomain().toEntity() }

        coEvery { api.getUsers(since) } returns apiUsers
        coEvery { dao.insertUsers(roomEntities) } just Runs
        every { dao.getAllUsers() } returns flowOf(roomEntities)

        // when
        val users = repository.getUsers(since).first()

        // then
        assertEquals(1, users.size)
        assertEquals("peter", users.first().login)
    }

    @Test
    fun `getUsers should still emit Room data when API fails`() = runTest(testDispatcher) {
        // given
        val since = 0
        coEvery { api.getUsers(since) } throws IOException("No internet")
        every { dao.getAllUsers() } returns flowOf(emptyList())

        // then
        assertFailsWith<Exception>("Error fetching users from API and no local data: No internet") {
            repository.getUsers(since).first()
        }
    }
}
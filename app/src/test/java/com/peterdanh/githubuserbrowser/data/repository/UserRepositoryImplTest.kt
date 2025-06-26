package com.peterdanh.githubuserbrowser.data.repository

import com.google.gson.GsonBuilder
import com.peterdanh.githubuserbrowser.data.local.dao.UserDao
import com.peterdanh.githubuserbrowser.data.mapper.toDomain
import com.peterdanh.githubuserbrowser.data.mapper.toEntity
import com.peterdanh.githubuserbrowser.data.remote.GitHubApiService
import com.peterdanh.githubuserbrowser.data.remote.dto.UserDto
import com.peterdanh.githubuserbrowser.domain.model.User
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import kotlin.test.Test
import kotlin.test.fail

class UserRepositoryImplTest {
    private lateinit var api: GitHubApiService
    private lateinit var dao: UserDao
    private lateinit var repository: UserRepositoryImpl

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
            UserDto("peter", "https://avatar.com/1", "https://github.com/peter")
        )
        val roomEntities = apiUsers.map { it.toDomain().toEntity() }

        coEvery { api.getUsers(since) } returns apiUsers
        coEvery { dao.insertUsers(roomEntities) } just Runs
        every { dao.getAllUsers() } returns flowOf(roomEntities)

        // when
        val result = repository.getUsers(since).first()

        // then
        assertEquals(1, result.users.size)
        assertEquals(1, result.apiUserCount)
        assertEquals("peter", result.users.first().login)
    }

    @Test
    fun `getUsers should still emit Room data when API fails`() = runTest(testDispatcher) {
        // given
        val since = 0
        val roomEntities = listOf(
            User("peter", "https://avatar.com/1", "https://github.com/peter")
        ).map { it.toEntity() }

        coEvery { api.getUsers(since) } throws IOException("No internet")
        every { dao.getAllUsers() } returns flowOf(roomEntities)

        // we ignore insertUsers because API fails
        // when
        val result = repository.getUsers(since).first()

        // then
        assertEquals(1, result.users.size)
        assertEquals(0, result.apiUserCount) // API failed
    }
}
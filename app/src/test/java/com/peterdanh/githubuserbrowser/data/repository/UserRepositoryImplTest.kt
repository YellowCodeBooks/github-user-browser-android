package com.peterdanh.githubuserbrowser.data.repository

import com.google.gson.GsonBuilder
import com.peterdanh.githubuserbrowser.data.remote.GitHubApiService
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.test.Test
import kotlin.test.fail

class UserRepositoryImplTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: GitHubApiService
    private lateinit var repository: UserRepositoryImpl

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val gson = GsonBuilder().create()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(GitHubApiService::class.java)

        repository = UserRepositoryImpl(apiService)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getUsers returns correct user list`() = runBlocking {
        val mockJson = """
            [
              {
                "login": "mojombo",
                "avatar_url": "https://avatars.githubusercontent.com/u/1?v=4",
                "html_url": "https://github.com/mojombo"
              },
              {
                "login": "defunkt",
                "avatar_url": "https://avatars.githubusercontent.com/u/2?v=4",
                "html_url": "https://github.com/defunkt"
              }
            ]
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockJson)
        )

        val result = repository.getUsers(0)

        assertEquals(2, result.size)
        assertEquals("mojombo", result[0].login)
        assertEquals("defunkt", result[1].login)
    }

    @Test
    fun `getUsers throws exception on 404 Not Found`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(404)
        )

        try {
            repository.getUsers(0)
            fail("Expected exception not thrown")
        } catch (e: Exception) {
            assertTrue(e.message!!.contains("404"))
        }
    }

    @Test
    fun `getUsers throws exception on 500 Internal Server Error`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(500)
                .setBody("Internal Server Error")
        )

        try {
            repository.getUsers(0)
            fail("Expected exception not thrown")
        } catch (e: Exception) {
            assertTrue(e.message!!.contains("500"))
        }
    }
}
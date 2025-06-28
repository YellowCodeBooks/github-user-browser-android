package com.peterdanh.githubuserbrowser.data.remote

import com.peterdanh.githubuserbrowser.data.remote.dto.UserDetailDto
import com.peterdanh.githubuserbrowser.data.remote.dto.UserDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit service interface for accessing GitHub user-related API endpoints.
 */
interface GitHubApiService {

    /**
     * Retrieves a list of users from the GitHub API.
     *
     * @param since The user ID to start the list from (for pagination).
     * @param perPage The number of users to retrieve per page (default is 20).
     * @return A list of [UserDto] objects representing users.
     */
    @GET("users")
    suspend fun getUsers(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int = 20
    ): List<UserDto>

    /**
     * Retrieves detailed information for a specific user.
     *
     * @param username The username of the user to retrieve details for.
     * @return A [UserDetailDto] containing detailed user information.
     */
    @GET("users/{username}")
    suspend fun getUserDetail(
        @Path("username") username: String
    ): UserDetailDto
}
package com.peterdanh.githubuserbrowser.data.remote

import com.peterdanh.githubuserbrowser.data.remote.dto.UserDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApiService {
    @GET("users")
    suspend fun getUsers(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int = 20
    ): List<UserDto>
}
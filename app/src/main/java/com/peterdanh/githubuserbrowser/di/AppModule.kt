package com.peterdanh.githubuserbrowser.di

import com.peterdanh.githubuserbrowser.data.local.dao.UserDao
import com.peterdanh.githubuserbrowser.data.remote.GitHubApiService
import com.peterdanh.githubuserbrowser.data.repository.UserRepositoryImpl
import com.peterdanh.githubuserbrowser.domain.repository.UserRepository
import com.peterdanh.githubuserbrowser.domain.usecase.GetUserDetailUseCase
import com.peterdanh.githubuserbrowser.domain.usecase.GetUsersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Dagger Hilt module that provides application-level dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Provides a singleton instance of [GitHubApiService] using Retrofit.
     *
     * @return The [GitHubApiService] implementation.
     */
    @Provides
    @Singleton
    fun provideGitHubApi(): GitHubApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubApiService::class.java)
    }

    /**
     * Provides a singleton instance of [UserRepository].
     *
     * @param api The [GitHubApiService] for remote data access.
     * @param userDao The [UserDao] for local data access.
     * @return The [UserRepository] implementation.
     */
    @Provides
    @Singleton
    fun provideUserRepository(
        api: GitHubApiService,
        userDao: UserDao
    ): UserRepository {
        return UserRepositoryImpl(api, userDao)
    }

    /**
     * Provides a singleton instance of [GetUsersUseCase].
     *
     * @param repository The [UserRepository] to use for retrieving users.
     * @return The [GetUsersUseCase] instance.
     */
    @Provides
    @Singleton
    fun provideGetUsersUseCase(repository: UserRepository): GetUsersUseCase {
        return GetUsersUseCase(repository)
    }

    /**
     * Provides a singleton instance of [GetUserDetailUseCase].
     *
     * @param repository The [UserRepository] to use for retrieving user details.
     * @return The [GetUserDetailUseCase] instance.
     */
    @Provides
    @Singleton
    fun provideGetUserDetailUseCase(repository: UserRepository): GetUserDetailUseCase {
        return GetUserDetailUseCase(repository)
    }
}
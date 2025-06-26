package com.peterdanh.githubuserbrowser.di

import com.peterdanh.githubuserbrowser.data.remote.GitHubApiService
import com.peterdanh.githubuserbrowser.data.repository.UserRepositoryImpl
import com.peterdanh.githubuserbrowser.domain.repository.UserRepository
import com.peterdanh.githubuserbrowser.domain.usecase.GetUsersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideGitHubApi(): GitHubApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepository(api: GitHubApiService): UserRepository {
        return UserRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideGetUsersUseCase(repository: UserRepository): GetUsersUseCase {
        return GetUsersUseCase(repository)
    }
}
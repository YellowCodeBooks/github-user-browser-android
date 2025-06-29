package com.peterdanh.githubuserbrowser.data.mapper

import com.peterdanh.githubuserbrowser.data.local.entity.UserEntity
import com.peterdanh.githubuserbrowser.data.remote.dto.UserDetailDto
import com.peterdanh.githubuserbrowser.data.remote.dto.UserDto
import com.peterdanh.githubuserbrowser.domain.model.User
import com.peterdanh.githubuserbrowser.domain.model.UserDetail

// UserDto -> Domain
fun UserDto.toDomain(): User = User(
    id = id,
    login = login,
    avatarUrl = avatar_url,
    htmlUrl = html_url
)

// UserDetailDto -> Domain
fun UserDetailDto.toDomain(): UserDetail = UserDetail(
    login = login,
    avatarUrl = avatar_url,
    htmlUrl = html_url,
    location = location,
    followers = followers,
    following = following
)

// Entity -> Domain
fun UserEntity.toDomain(): User = User(
    id = id,
    login = login,
    avatarUrl = avatarUrl,
    htmlUrl = htmlUrl
)

// Domain -> Entity
fun User.toEntity(): UserEntity = UserEntity(
    id = id,
    login = login,
    avatarUrl = avatarUrl,
    htmlUrl = htmlUrl
)
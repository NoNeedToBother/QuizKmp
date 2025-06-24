package ru.kpfu.itis.quiz.feature.profile.domain.model

import kotlinx.serialization.Serializable

data class UserWithResults(
    val user: User,
    val results: List<Result>
)

@Serializable
data class User(
    val id: Long,
    val username: String,
    val profilePictureUri: String,
    val info: String,
    val dateRegistered: String,
)

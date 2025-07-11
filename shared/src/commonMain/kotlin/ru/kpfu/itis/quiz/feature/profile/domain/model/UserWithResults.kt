package ru.kpfu.itis.quiz.feature.profile.domain.model

data class UserWithResults(
    val user: User,
    val results: List<Result>
)

data class User(
    val id: Long,
    val username: String,
    val profilePictureUri: String,
    val info: String,
    val dateRegistered: String,
)

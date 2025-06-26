package ru.kpfu.itis.quiz.feature.profile.presentation.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long,
    val username: String,
    val profilePictureUri: String,
    val info: String,
    val dateRegistered: String,
)

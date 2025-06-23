package ru.kpfu.itis.quiz.core.model

data class User(
    val id: Long,
    val username: String,
    val profilePictureUri: String,
    val info: String,
    val dateRegistered: String,
)

package ru.kpfu.itis.quiz.feature.authentication.presentation.signin.mvi

import kotlinx.serialization.Serializable

@Serializable
data class SignInScreenState(
    val username: String = "",
    val isLoading: Boolean = false,
    val passwordError: String? = null,
    val usernameError: String? = null,
)

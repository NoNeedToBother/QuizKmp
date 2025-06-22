package ru.kpfu.itis.quiz.feature.authentication.presentation.register.mvi

import kotlinx.serialization.Serializable

@Serializable
data class RegisterScreenState(
    val username: String = "",
    val isLoading: Boolean = false,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val usernameError: String? = null,
)

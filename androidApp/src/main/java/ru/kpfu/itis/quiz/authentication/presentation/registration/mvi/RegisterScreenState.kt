package ru.kpfu.itis.quiz.authentication.presentation.registration.mvi

import ru.kpfu.itis.quiz.core.core.model.User

data class RegisterScreenState(
    val user: User?,
    val isLoading: Boolean = false,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val usernameError: String? = null,
    val emailError: String? = null
)

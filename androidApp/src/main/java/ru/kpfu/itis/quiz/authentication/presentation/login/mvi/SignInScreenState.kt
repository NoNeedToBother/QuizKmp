package ru.kpfu.itis.quiz.authentication.presentation.login.mvi

import ru.kpfu.itis.quiz.core.core.model.User

data class SignInScreenState(
    val userData: User?,
    val isLoading: Boolean = false,
    val passwordError: String? = null,
    val emailError: String? = null,
)

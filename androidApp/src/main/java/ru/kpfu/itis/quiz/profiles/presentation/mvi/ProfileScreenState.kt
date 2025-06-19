package ru.kpfu.itis.quiz.profiles.presentation.mvi

import ru.kpfu.itis.quiz.core.core.model.User

data class ProfileScreenState(
    val user: User? = null,
    val processingCredentials: Boolean = false,
    val usernameError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val emailError: String? = null
)

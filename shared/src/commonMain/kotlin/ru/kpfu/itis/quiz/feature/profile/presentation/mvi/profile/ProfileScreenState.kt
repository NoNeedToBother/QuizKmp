package ru.kpfu.itis.quiz.feature.profile.presentation.mvi.profile

import kotlinx.serialization.Serializable
import ru.kpfu.itis.quiz.feature.profile.presentation.model.Result
import ru.kpfu.itis.quiz.feature.profile.presentation.model.User

@Serializable
data class ProfileScreenState(
    val user: User? = null,
    val results: List<Result> = emptyList(),
    val processingCredentials: Boolean = false,
    val usernameError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val emailError: String? = null
)

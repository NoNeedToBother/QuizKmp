package ru.kpfu.itis.quiz.feature.authentication.presentation.signin.mvi

sealed interface SignInScreenIntent {
    data class AuthenticateUser(val username: String, val password: String) : SignInScreenIntent
    data object GetSignedInUser : SignInScreenIntent
    data class ValidatePassword(val password: String) : SignInScreenIntent
    data class ValidateUsername(val username: String) : SignInScreenIntent
}

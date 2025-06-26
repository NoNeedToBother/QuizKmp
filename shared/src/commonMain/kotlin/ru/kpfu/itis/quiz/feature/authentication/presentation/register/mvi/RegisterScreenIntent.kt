package ru.kpfu.itis.quiz.feature.authentication.presentation.register.mvi

sealed interface RegisterScreenIntent {
    data class RegisterUser(val username: String, val password: String,
                            val confirmPassword: String) : RegisterScreenIntent
    data object GetSignedInUser : RegisterScreenIntent
    data class ValidatePassword(val password: String) : RegisterScreenIntent
    data class ValidateConfirmPassword(val password: String) : RegisterScreenIntent
    data class ValidateUsername(val username: String) : RegisterScreenIntent
}

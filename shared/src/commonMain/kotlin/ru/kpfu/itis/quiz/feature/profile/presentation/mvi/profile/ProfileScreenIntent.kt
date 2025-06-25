package ru.kpfu.itis.quiz.feature.profile.presentation.mvi.profile

sealed interface ProfileScreenIntent {
    data object GetCurrent : ProfileScreenIntent
    data class UpdatePassword(val password: String) : ProfileScreenIntent
    data class UpdateProfilePicture(val uri: String) : ProfileScreenIntent
    data class UpdateUserInfo(val username: String?, val info: String?) : ProfileScreenIntent
    data class ValidateUsername(val username: String?) : ProfileScreenIntent
    data class ValidatePassword(val password: String?) : ProfileScreenIntent
    data class ValidateConfirmPassword(val confirmPassword: String?) : ProfileScreenIntent
    data object ClearErrors : ProfileScreenIntent
    data object Logout : ProfileScreenIntent
    data class ConfirmProfilePicture(val uri: String) : ProfileScreenIntent
}

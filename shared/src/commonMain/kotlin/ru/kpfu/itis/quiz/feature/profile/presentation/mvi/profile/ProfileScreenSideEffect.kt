package ru.kpfu.itis.quiz.feature.profile.presentation.mvi.profile

sealed class ProfileScreenSideEffect {
    data class ShowError(val title: String, val message: String): ProfileScreenSideEffect()
    data object GoToSignIn: ProfileScreenSideEffect()
    //data object CredentialsConfirmed: ProfileScreenSideEffect()
    data class ProfilePictureConfirmed(val uri: String): ProfileScreenSideEffect()
}

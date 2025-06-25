package ru.kpfu.itis.quiz.feature.profile.presentation.mvi.other

sealed class OtherUserProfileScreenSideEffect {
    data class ShowError(val title: String, val message: String): OtherUserProfileScreenSideEffect()
}

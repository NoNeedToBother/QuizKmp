package ru.kpfu.itis.quiz.feature.authentication.presentation.register.mvi

sealed interface RegisterScreenSideEffect {
    data object NavigateToMainMenu: RegisterScreenSideEffect
    data class ShowError(val title: String, val message: String): RegisterScreenSideEffect
}

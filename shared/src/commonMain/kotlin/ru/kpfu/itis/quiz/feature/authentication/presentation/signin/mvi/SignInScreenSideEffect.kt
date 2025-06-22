package ru.kpfu.itis.quiz.feature.authentication.presentation.signin.mvi

sealed interface SignInScreenSideEffect {
    data object NavigateToMainMenu: SignInScreenSideEffect
    data class ShowError(val title: String, val message: String): SignInScreenSideEffect
}

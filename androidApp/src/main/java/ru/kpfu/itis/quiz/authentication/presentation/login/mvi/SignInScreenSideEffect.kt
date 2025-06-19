package ru.kpfu.itis.quiz.authentication.presentation.login.mvi

sealed class SignInScreenSideEffect {
    data object NavigateToMainMenu: SignInScreenSideEffect()
    data class ShowError(val title: String, val message: String): SignInScreenSideEffect()
}

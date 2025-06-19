package ru.kpfu.itis.quiz.users.presentation.mvi

sealed class SearchUsersScreenSideEffect {
    data class ShowError(val title: String, val message: String): SearchUsersScreenSideEffect()
}

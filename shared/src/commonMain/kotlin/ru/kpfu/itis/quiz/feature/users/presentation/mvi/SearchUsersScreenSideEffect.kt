package ru.kpfu.itis.quiz.feature.users.presentation.mvi

sealed interface SearchUsersScreenSideEffect {
    data class ShowError(val title: String, val message: String): SearchUsersScreenSideEffect
}

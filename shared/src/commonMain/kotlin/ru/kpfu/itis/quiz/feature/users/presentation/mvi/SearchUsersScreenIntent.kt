package ru.kpfu.itis.quiz.feature.users.presentation.mvi

sealed interface SearchUsersScreenIntent {
    data class SearchUsers(val query: String, val limit: Int) : SearchUsersScreenIntent
}
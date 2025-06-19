package ru.kpfu.itis.quiz.users.presentation.mvi

import ru.kpfu.itis.quiz.core.core.model.User

data class SearchUsersScreenState(
    val users: List<User> = emptyList(),
    val loadingEnded: Boolean = false
)

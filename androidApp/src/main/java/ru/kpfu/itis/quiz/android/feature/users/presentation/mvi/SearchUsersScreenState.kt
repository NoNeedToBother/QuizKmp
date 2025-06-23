package ru.kpfu.itis.quiz.android.feature.users.presentation.mvi

import ru.kpfu.itis.quiz.core.model.User

data class SearchUsersScreenState(
    val users: List<User> = emptyList(),
    val loadingEnded: Boolean = false
)

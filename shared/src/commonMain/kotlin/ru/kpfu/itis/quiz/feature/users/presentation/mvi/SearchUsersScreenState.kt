package ru.kpfu.itis.quiz.feature.users.presentation.mvi

import app.cash.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import ru.kpfu.itis.quiz.core.model.User

@Serializable
data class SearchUsersScreenState(
    val loadingEnded: Boolean = false,
    @Transient
    val users: Flow<PagingData<User>> = emptyFlow()
)

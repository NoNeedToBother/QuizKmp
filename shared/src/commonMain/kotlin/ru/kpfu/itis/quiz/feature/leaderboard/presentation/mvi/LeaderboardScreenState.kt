package ru.kpfu.itis.quiz.feature.leaderboard.presentation.mvi

import app.cash.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import ru.kpfu.itis.quiz.feature.leaderboard.presentation.model.QuestionSettingsUi
import ru.kpfu.itis.quiz.feature.leaderboard.presentation.model.Result

@Serializable
data class LeaderboardScreenState(
    @Transient
    val results: Flow<PagingData<Result>> = emptyFlow(),
    val settings: QuestionSettingsUi? = null,
    val loadingEnded: Boolean = false
)

package ru.kpfu.itis.quiz.android.feature.leaderboard.presentation.mvi

import ru.kpfu.itis.quiz.core.model.QuestionSettings
import ru.kpfu.itis.quiz.core.model.Result

data class LeaderboardsScreenState(
    val results: List<Result> = emptyList(),
    val settings: QuestionSettings? = null,
    val loadingEnded: Boolean = false
)

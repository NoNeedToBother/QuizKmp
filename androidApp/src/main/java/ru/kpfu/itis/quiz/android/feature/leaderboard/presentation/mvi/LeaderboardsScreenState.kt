package ru.kpfu.itis.quiz.android.feature.leaderboard.presentation.mvi

import ru.kpfu.itis.quiz.core.model.Result
import ru.kpfu.itis.quiz.android.feature.questions.presentation.model.QuestionSettings

data class LeaderboardsScreenState(
    val results: List<Result> = emptyList(),
    val settings: QuestionSettings? = null,
    val loadingEnded: Boolean = false
)

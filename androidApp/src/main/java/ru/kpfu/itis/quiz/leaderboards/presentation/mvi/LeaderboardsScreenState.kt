package ru.kpfu.itis.quiz.leaderboards.presentation.mvi

import ru.kpfu.itis.quiz.core.core.model.Result
import ru.kpfu.itis.quiz.questions.presentation.model.QuestionSettings

data class LeaderboardsScreenState(
    val results: List<Result> = emptyList(),
    val settings: QuestionSettings? = null,
    val loadingEnded: Boolean = false
)

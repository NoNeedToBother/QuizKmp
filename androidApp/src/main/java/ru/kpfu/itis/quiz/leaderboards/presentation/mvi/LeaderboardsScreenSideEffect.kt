package ru.kpfu.itis.quiz.leaderboards.presentation.mvi

sealed class LeaderboardsScreenSideEffect {
    data class ShowError(val title: String, val message: String): LeaderboardsScreenSideEffect()
}

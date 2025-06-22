package ru.kpfu.itis.quiz.android.feature.leaderboard.presentation.mvi

sealed class LeaderboardsScreenSideEffect {
    data class ShowError(val title: String, val message: String): LeaderboardsScreenSideEffect()
}

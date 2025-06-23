package ru.kpfu.itis.quiz.feature.leaderboard.presentation.mvi

sealed interface LeaderboardScreenSideEffect {
    data class ShowError(val title: String, val message: String): LeaderboardScreenSideEffect
}

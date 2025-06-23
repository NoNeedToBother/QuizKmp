package ru.kpfu.itis.quiz.feature.leaderboard.presentation.mvi

sealed interface LeaderboardScreenIntent {
    data object SaveSettings : LeaderboardScreenIntent
    data class GetLeaderboard(val limit: Int) : LeaderboardScreenIntent
    data class ChangeDifficulty(val difficulty: String) : LeaderboardScreenIntent
    data class ChangeCategory(val category: String) : LeaderboardScreenIntent
    data class ChangeGameMode(val gameMode: String) : LeaderboardScreenIntent

}
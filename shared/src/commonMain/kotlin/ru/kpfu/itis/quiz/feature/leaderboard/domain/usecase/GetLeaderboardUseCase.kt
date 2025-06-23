package ru.kpfu.itis.quiz.feature.leaderboard.domain.usecase

import app.cash.paging.Pager
import ru.kpfu.itis.quiz.core.model.Category
import ru.kpfu.itis.quiz.core.model.Difficulty
import ru.kpfu.itis.quiz.core.model.GameMode
import ru.kpfu.itis.quiz.core.model.Result

interface GetLeaderboardUseCase {
    suspend operator fun invoke(
        gameMode: GameMode,
        difficulty: Difficulty?,
        category: Category?,
        limit: Int
    ): Pager<Int, Result>
}

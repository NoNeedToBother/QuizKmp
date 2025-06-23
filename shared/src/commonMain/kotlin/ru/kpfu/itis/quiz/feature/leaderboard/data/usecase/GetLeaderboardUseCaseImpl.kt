package ru.kpfu.itis.quiz.feature.leaderboard.data.usecase

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import ru.kpfu.itis.quiz.core.model.Category
import ru.kpfu.itis.quiz.core.model.Difficulty
import ru.kpfu.itis.quiz.core.model.GameMode
import ru.kpfu.itis.quiz.core.model.Result
import ru.kpfu.itis.quiz.feature.leaderboard.domain.paging.ResultPagingSource
import ru.kpfu.itis.quiz.feature.leaderboard.domain.repository.ResultRepository
import ru.kpfu.itis.quiz.feature.leaderboard.domain.usecase.GetLeaderboardUseCase

class GetLeaderboardUseCaseImpl(
    private val resultRepository: ResultRepository
): GetLeaderboardUseCase {

    override suspend operator fun invoke(
        gameMode: GameMode,
        difficulty: Difficulty?,
        category: Category?,
        limit: Int
    ): Pager<Int, Result> {
        return Pager(
            PagingConfig(
                pageSize = limit,
            )
        ) {
            ResultPagingSource(resultRepository).apply {
                this.gameMode = gameMode
                this.category = category
                this.difficulty = difficulty
            }
        }
    }
}
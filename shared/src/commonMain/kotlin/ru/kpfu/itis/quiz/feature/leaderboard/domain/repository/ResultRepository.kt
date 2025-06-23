package ru.kpfu.itis.quiz.feature.leaderboard.domain.repository

import ru.kpfu.itis.quiz.core.model.Category
import ru.kpfu.itis.quiz.core.model.Difficulty
import ru.kpfu.itis.quiz.core.model.GameMode
import ru.kpfu.itis.quiz.core.model.Result

interface ResultRepository {

    suspend fun getResults(
        gameMode: GameMode,
        difficulty: Difficulty?,
        category: Category?,
        limit: Int, offset: Int
    ): List<Result>

}
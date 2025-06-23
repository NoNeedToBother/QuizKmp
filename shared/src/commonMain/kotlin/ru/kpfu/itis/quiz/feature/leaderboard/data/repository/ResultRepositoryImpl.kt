package ru.kpfu.itis.quiz.feature.leaderboard.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import ru.kpfu.itis.quiz.core.database.AppDatabase
import ru.kpfu.itis.quiz.core.model.Category
import ru.kpfu.itis.quiz.core.model.Difficulty
import ru.kpfu.itis.quiz.core.model.GameMode
import ru.kpfu.itis.quiz.core.model.Result
import ru.kpfu.itis.quiz.feature.leaderboard.data.mapper.mapResultAndUserEntity
import ru.kpfu.itis.quiz.feature.leaderboard.domain.repository.ResultRepository

class ResultRepositoryImpl(
    private val database: AppDatabase,
) : ResultRepository {
    override suspend fun getResults(
        gameMode: GameMode,
        difficulty: Difficulty?,
        category: Category?,
        limit: Int,
        offset: Int
    ): List<Result> {
        return withContext(Dispatchers.IO) {
            val dao = database.resultDao()

            val result = difficulty?.let {
                category?.let {
                    dao.getByGameModeDifficultyAndCategoryWithPaging(
                        gameMode = gameMode.name, category = category.name, difficulty = difficulty.name,
                        limit = limit, offset = offset
                    )
                } ?: dao.getByGameModeAndDifficultyWithPaging(
                    gameMode = gameMode.name, difficulty = difficulty.name,
                    limit = limit, offset = offset
                )
            } ?: category?.let {
                dao.getByGameModeAndCategoryWithPaging(
                    gameMode = gameMode.name, category = category.name,
                    limit = limit, offset = offset
                )
            } ?: dao.getByGameModeWithPaging(
                gameMode = gameMode.name, limit = limit, offset = offset
            )

            result.map { mapResultAndUserEntity(it) }
        }
    }
}

package ru.kpfu.itis.quiz.feature.questions.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import ru.kpfu.itis.quiz.core.database.AppDatabase
import ru.kpfu.itis.quiz.core.database.entity.ResultEntity
import ru.kpfu.itis.quiz.core.model.GameMode
import ru.kpfu.itis.quiz.feature.questions.domain.model.Result
import ru.kpfu.itis.quiz.feature.questions.domain.repository.ResultRepository
import kotlin.math.E
import kotlin.math.pow

class ResultRepositoryImpl(
    private val appDatabase: AppDatabase,
) : ResultRepository {

    override suspend fun getMaxScore(): Double {
        return MAX_SCORE.toDouble()
    }

    override suspend fun save(result: Result, userId: Long): Double {
        return withContext(Dispatchers.IO) {
            val score = calculateScore(
                time = result.time, gameMode = result.gameMode,
                total = result.total, correct = result.correct
            )
            val dao = appDatabase.resultDao()
            dao.save(
                ResultEntity(
                    time = result.time,
                    correct = result.correct,
                    total = result.total,
                    difficulty = result.difficulty.name,
                    category = result.category.name,
                    gameMode = result.gameMode.name,
                    score = score,
                    userId = userId,
                )
            )
            score
        }
    }

    private fun calculateScore(time: Int, correct: Int, total: Int, gameMode: GameMode): Double {
        val ratio = correct.toDouble() / total
        val ratioValue = E.pow(ratio)
        val gameModeFactor =
            when(gameMode) {
                GameMode.BLITZ -> BLITZ_FACTOR
                GameMode.CHALLENGE -> CHALLENGE_FACTOR
                GameMode.EXPERT -> EXPERT_CHALLENGE
            }
        val timeValue = E.pow((-1) * (time * gameModeFactor).pow(TIME_POW_FACTOR))

        return ratioValue * (timeValue + 1) / 2 / E * MAX_SCORE
    }

    companion object {
        private const val BLITZ_FACTOR = (1).toDouble() / 10 / 5
        private const val CHALLENGE_FACTOR = (1).toDouble() / 15 / 8
        private const val EXPERT_CHALLENGE = (1).toDouble() / 25 / 10
        private const val TIME_POW_FACTOR = 4

        private const val MAX_SCORE = 10
    }
}

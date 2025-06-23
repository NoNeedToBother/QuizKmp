package ru.kpfu.itis.quiz.feature.questions.domain.repository

import ru.kpfu.itis.quiz.feature.questions.domain.model.Result

interface ResultRepository {
    suspend fun getMaxScore(): Double

    suspend fun save(result: Result, userId: Long): Double
}

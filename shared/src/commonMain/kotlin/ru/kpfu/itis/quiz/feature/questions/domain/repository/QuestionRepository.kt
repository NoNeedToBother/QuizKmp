package ru.kpfu.itis.quiz.feature.questions.domain.repository

import ru.kpfu.itis.quiz.core.model.Category
import ru.kpfu.itis.quiz.core.model.Difficulty
import ru.kpfu.itis.quiz.feature.questions.domain.model.Questions

interface QuestionRepository {
    suspend fun getCategoryCode(category: Category): Int

    suspend fun getQuestions(
        amount: Int,
        difficulty: Difficulty,
        category: Int
    ): Questions
}
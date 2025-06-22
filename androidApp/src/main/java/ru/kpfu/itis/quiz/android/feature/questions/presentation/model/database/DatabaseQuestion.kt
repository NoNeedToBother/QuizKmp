package ru.kpfu.itis.quiz.android.feature.questions.presentation.model.database

import ru.kpfu.itis.quiz.core.model.Category
import ru.kpfu.itis.quiz.core.model.Difficulty

data class DatabaseQuestion(
    val text: String,
    val difficulty: Difficulty,
    val category: Category,
    val answers: List<DatabaseAnswer>
)

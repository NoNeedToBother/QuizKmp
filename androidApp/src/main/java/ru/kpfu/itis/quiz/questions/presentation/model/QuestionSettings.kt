package ru.kpfu.itis.quiz.questions.presentation.model

import ru.kpfu.itis.quiz.core.core.model.Category
import ru.kpfu.itis.quiz.core.core.model.Difficulty
import ru.kpfu.itis.quiz.core.core.model.GameMode

data class QuestionSettings(
    val difficulty: Difficulty,
    val category: Category,
    val gameMode: GameMode
)

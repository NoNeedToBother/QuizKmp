package ru.kpfu.itis.quiz.questions.presentation.model

import ru.kpfu.itis.quiz.core.core.model.Category
import ru.kpfu.itis.quiz.core.core.model.Difficulty
import ru.kpfu.itis.quiz.core.core.model.GameMode
import ru.kpfu.itis.quiz.core.core.model.User

class Result(
    val user: User,
    val time: Int,
    val correct: Int,
    val total: Int,
    val difficulty: Difficulty,
    val category: Category,
    val gameMode: GameMode
)

package ru.kpfu.itis.quiz.feature.questions.domain.model

import ru.kpfu.itis.quiz.core.model.Category
import ru.kpfu.itis.quiz.core.model.Difficulty
import ru.kpfu.itis.quiz.core.model.GameMode
import ru.kpfu.itis.quiz.core.model.User

class Result(
    val user: User,
    val time: Int,
    val correct: Int,
    val total: Int,
    val difficulty: Difficulty,
    val category: Category,
    val gameMode: GameMode
)

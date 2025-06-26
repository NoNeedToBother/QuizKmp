package ru.kpfu.itis.quiz.feature.leaderboard.presentation.model

import ru.kpfu.itis.quiz.core.model.Category
import ru.kpfu.itis.quiz.core.model.Difficulty
import ru.kpfu.itis.quiz.core.model.GameMode

class Result(
    val id: Long,
    val user: User,
    val time: Int,
    val correct: Int,
    val total: Int,
    val score: Double,
    val difficulty: Difficulty,
    val category: Category,
    val gameMode: GameMode
)

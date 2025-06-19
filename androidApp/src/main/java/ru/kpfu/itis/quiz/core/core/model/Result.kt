package ru.kpfu.itis.quiz.core.core.model

import ru.kpfu.itis.quiz.core.core.utils.DateTime

class Result(
    val id: String,
    val user: User,
    val time: Int,
    val correct: Int,
    val total: Int,
    val score: Double,
    val date: DateTime,
    val difficulty: Difficulty,
    val category: Category,
    val gameMode: GameMode
)

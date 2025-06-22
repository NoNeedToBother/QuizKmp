package ru.kpfu.itis.quiz.core.model

import kotlinx.datetime.LocalDateTime

class Result(
    val id: String,
    val user: User,
    val time: Int,
    val correct: Int,
    val total: Int,
    val score: Double,
    val date: LocalDateTime,
    val difficulty: Difficulty,
    val category: Category,
    val gameMode: GameMode
)

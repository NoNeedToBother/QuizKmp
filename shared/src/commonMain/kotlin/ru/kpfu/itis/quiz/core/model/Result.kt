package ru.kpfu.itis.quiz.core.model

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

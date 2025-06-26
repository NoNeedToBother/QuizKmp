package ru.kpfu.itis.quiz.core.model

data class QuestionSettings(
    val difficulty: Difficulty,
    val category: Category,
    val gameMode: GameMode
)

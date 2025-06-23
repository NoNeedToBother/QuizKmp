package ru.kpfu.itis.quiz.core.model

import kotlinx.serialization.Serializable

@Serializable
data class QuestionSettings(
    val difficulty: Difficulty,
    val category: Category,
    val gameMode: GameMode
)

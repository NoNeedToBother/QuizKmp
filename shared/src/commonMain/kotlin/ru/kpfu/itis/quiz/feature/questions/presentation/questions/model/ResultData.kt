package ru.kpfu.itis.quiz.feature.questions.presentation.questions.model

import kotlinx.serialization.Serializable

@Serializable
data class ResultData(
    val difficulty: String,
    val category: String,
    val gameMode: String,
    val time: Int,
    val correct: Int,
    val total: Int,
    val score: Double
)

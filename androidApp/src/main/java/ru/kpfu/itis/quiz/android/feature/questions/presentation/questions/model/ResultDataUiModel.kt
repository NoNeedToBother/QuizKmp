package ru.kpfu.itis.quiz.android.feature.questions.presentation.questions.model

data class ResultDataUiModel(
    val difficulty: String,
    val category: String,
    val gameMode: String,
    val time: Int,
    val correct: Int,
    val total: Int,
    val score: Double
)

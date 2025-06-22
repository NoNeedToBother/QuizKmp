package ru.kpfu.itis.quiz.android.feature.questions.presentation.model

data class AnswerData(
    val answer: String,
    var chosen: Boolean,
    val correct: Boolean
)

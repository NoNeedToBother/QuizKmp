package ru.kpfu.itis.quiz.questions.presentation.model

data class AnswerData(
    val answer: String,
    var chosen: Boolean,
    val correct: Boolean
)

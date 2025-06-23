package ru.kpfu.itis.quiz.feature.questions.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AnswerData(
    val answer: String,
    var chosen: Boolean,
    val correct: Boolean
)

package ru.kpfu.itis.quiz.feature.questions.domain.model

import kotlinx.serialization.Serializable

data class Questions(
    val questions: List<QuestionData>
)

@Serializable
data class QuestionData(
    val text: String,
    val answer: String,
    val incorrectAnswers: List<String>
)

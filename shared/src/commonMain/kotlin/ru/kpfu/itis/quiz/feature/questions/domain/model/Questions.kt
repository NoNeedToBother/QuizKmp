package ru.kpfu.itis.quiz.feature.questions.domain.model

data class Questions(
    val questions: List<QuestionData>
)

data class QuestionData(
    val text: String,
    val answer: String,
    val incorrectAnswers: List<String>
)

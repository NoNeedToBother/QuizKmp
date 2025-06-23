package ru.kpfu.itis.quiz.feature.questions.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class QuestionResponse(
    @SerialName("response_code") val code: Int? = null,
    @SerialName("results") val questions: List<QuestionDataResponse>? = null
)

@Serializable
internal data class QuestionDataResponse(
    @SerialName("question") val text: String? = null,
    @SerialName("correct_answer") val answer: String? = null,
    @SerialName("incorrect_answers") val incorrectAnswers: List<String>? = null
)

package ru.kpfu.itis.quiz.feature.questions.presentation.questions.mvi

import kotlinx.serialization.Serializable
import ru.kpfu.itis.quiz.feature.questions.presentation.questions.model.QuestionDataUi
import ru.kpfu.itis.quiz.feature.questions.presentation.questions.model.ResultData

@Serializable
data class QuestionsScreenState(
    val questions: List<QuestionDataUi> = emptyList(),
    val time: Int = 0,
    val maxScore: Double = -1.0,
    val result: ResultData? = null
)

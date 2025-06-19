package ru.kpfu.itis.quiz.questions.presentation.questions.mvi

import ru.kpfu.itis.quiz.questions.presentation.model.QuestionData
import ru.kpfu.itis.quiz.questions.presentation.questions.model.ResultDataUiModel

data class QuestionsScreenState(
    val questions: List<QuestionData> = emptyList(),
    val time: Int = 0,
    val maxScore: Double = -1.0,
    val result: ResultDataUiModel? = null
)

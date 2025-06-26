package ru.kpfu.itis.quiz.feature.questions.presentation.questions.model

import kotlinx.serialization.Serializable
import ru.kpfu.itis.quiz.core.model.Category
import ru.kpfu.itis.quiz.core.model.Difficulty
import ru.kpfu.itis.quiz.core.model.GameMode

@Serializable
data class QuestionDataUi(
    val text: String,
    val answers: List<AnswerDataUi>,
    var difficulty: Difficulty? = null,
    var category: Category? = null,
    var gameMode: GameMode? = null,
)

@Serializable
data class AnswerDataUi(
    val answer: String,
    var chosen: Boolean,
    val correct: Boolean
)

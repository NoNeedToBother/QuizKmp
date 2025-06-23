package ru.kpfu.itis.quiz.feature.questions.presentation.questions.model

import kotlinx.serialization.Serializable
import ru.kpfu.itis.quiz.core.model.Category
import ru.kpfu.itis.quiz.core.model.Difficulty
import ru.kpfu.itis.quiz.core.model.GameMode
import ru.kpfu.itis.quiz.feature.questions.domain.model.AnswerData

@Serializable
data class QuestionDataUi(
    val text: String,
    val answers: List<AnswerData>,
    var difficulty: Difficulty? = null,
    var category: Category? = null,
    var gameMode: GameMode? = null,
)

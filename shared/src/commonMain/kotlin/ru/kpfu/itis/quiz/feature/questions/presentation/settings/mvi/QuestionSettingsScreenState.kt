package ru.kpfu.itis.quiz.feature.questions.presentation.settings.mvi

import kotlinx.serialization.Serializable
import ru.kpfu.itis.quiz.feature.questions.presentation.settings.model.QuestionSettings

@Serializable
data class QuestionSettingsScreenState(
    val settings: QuestionSettings? = null,
)

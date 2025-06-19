package ru.kpfu.itis.quiz.questions.presentation.settings.mvi

import ru.kpfu.itis.quiz.questions.presentation.model.QuestionSettings

data class QuestionSettingsScreenState(
    val settings: QuestionSettings? = null,
)

package ru.kpfu.itis.quiz.feature.questions.presentation.settings.mvi

sealed interface QuestionSettingsScreenSideEffect {
    data class ShowError(val title: String, val message: String): QuestionSettingsScreenSideEffect
}

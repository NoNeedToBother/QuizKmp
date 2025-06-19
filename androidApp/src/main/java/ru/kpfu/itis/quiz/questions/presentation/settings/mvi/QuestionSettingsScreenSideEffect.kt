package ru.kpfu.itis.quiz.questions.presentation.settings.mvi

sealed class QuestionSettingsScreenSideEffect {
    data class ShowError(val title: String, val message: String): QuestionSettingsScreenSideEffect()
}

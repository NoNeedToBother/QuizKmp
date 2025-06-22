package ru.kpfu.itis.quiz.android.feature.questions.presentation.settings.mvi

sealed class QuestionSettingsScreenSideEffect {
    data class ShowError(val title: String, val message: String): QuestionSettingsScreenSideEffect()
}

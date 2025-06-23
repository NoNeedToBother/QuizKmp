package ru.kpfu.itis.quiz.feature.questions.presentation.settings.mvi

sealed interface QuestionSettingsScreenIntent {
    data object GetQuestionSettings : QuestionSettingsScreenIntent
    data object SaveQuestionSettings : QuestionSettingsScreenIntent
    data class UpdateCategory(val category: String) : QuestionSettingsScreenIntent
    data class UpdateDifficulty(val difficulty: String) : QuestionSettingsScreenIntent
    data class UpdateGameMode(val gameMode: String) : QuestionSettingsScreenIntent
}
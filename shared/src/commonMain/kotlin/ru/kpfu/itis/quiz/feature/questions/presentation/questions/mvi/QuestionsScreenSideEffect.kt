package ru.kpfu.itis.quiz.feature.questions.presentation.questions.mvi

sealed interface QuestionsScreenSideEffect {
    data class ShowError(val title: String, val message: String): QuestionsScreenSideEffect
}

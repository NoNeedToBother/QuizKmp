package ru.kpfu.itis.quiz.questions.presentation.questions.mvi

sealed class QuestionsScreenSideEffect {
    data class ShowError(val title: String, val message: String): QuestionsScreenSideEffect()
}

package ru.kpfu.itis.quiz.feature.questions.presentation.questions.mvi

sealed interface QuestionsScreenIntent {
    data object GetMaxScore : QuestionsScreenIntent
    data object GetQuestions : QuestionsScreenIntent
    data class UpdateAnswers(val pos: Int, val chosenPos: Int) : QuestionsScreenIntent
    data object EndQuestions : QuestionsScreenIntent
}
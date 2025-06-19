package ru.kpfu.itis.quiz.profiles.presentation.mvi

import ru.kpfu.itis.quiz.core.core.model.Result

sealed class OtherUserProfileScreenSideEffect {
    data class ShowError(val title: String, val message: String): OtherUserProfileScreenSideEffect()
    data class ResultsReceived(val results: List<Result>): OtherUserProfileScreenSideEffect()
}

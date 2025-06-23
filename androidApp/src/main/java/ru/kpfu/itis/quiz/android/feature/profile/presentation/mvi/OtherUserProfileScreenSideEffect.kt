package ru.kpfu.itis.quiz.android.feature.profile.presentation.mvi

import ru.kpfu.itis.quiz.core.model.Result

sealed class OtherUserProfileScreenSideEffect {
    data class ShowError(val title: String, val message: String): OtherUserProfileScreenSideEffect()
    data class ResultsReceived(val results: List<Result>): OtherUserProfileScreenSideEffect()
}

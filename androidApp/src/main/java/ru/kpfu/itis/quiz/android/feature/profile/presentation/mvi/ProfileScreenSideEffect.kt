package ru.kpfu.itis.quiz.android.feature.profile.presentation.mvi

import android.net.Uri
import ru.kpfu.itis.quiz.core.model.Result
import ru.kpfu.itis.quiz.core.model.User

sealed class ProfileScreenSideEffect {
    data class ShowError(val title: String, val message: String): ProfileScreenSideEffect()
    data class ResultsReceived(val results: List<Result>): ProfileScreenSideEffect()
    data object GoToSignIn: ProfileScreenSideEffect()
    data class FriendRequestsReceived(val requests: List<User>): ProfileScreenSideEffect()
    data object CredentialsConfirmed: ProfileScreenSideEffect()
    data class ProfilePictureConfirmed(val uri: Uri): ProfileScreenSideEffect()
}

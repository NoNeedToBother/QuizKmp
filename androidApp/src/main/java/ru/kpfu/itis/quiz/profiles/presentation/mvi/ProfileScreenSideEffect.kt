package ru.kpfu.itis.quiz.profiles.presentation.mvi

import android.net.Uri
import ru.kpfu.itis.quiz.core.core.model.Result
import ru.kpfu.itis.quiz.core.core.model.User

sealed class ProfileScreenSideEffect {
    data class ShowError(val title: String, val message: String): ProfileScreenSideEffect()
    data class ResultsReceived(val results: List<Result>): ProfileScreenSideEffect()
    data object GoToSignIn: ProfileScreenSideEffect()
    data class FriendRequestsReceived(val requests: List<User>): ProfileScreenSideEffect()
    data object CredentialsConfirmed: ProfileScreenSideEffect()
    data class ProfilePictureConfirmed(val uri: Uri): ProfileScreenSideEffect()
}

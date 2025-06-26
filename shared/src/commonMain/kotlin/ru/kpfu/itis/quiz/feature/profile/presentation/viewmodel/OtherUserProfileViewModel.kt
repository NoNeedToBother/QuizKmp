package ru.kpfu.itis.quiz.feature.profile.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import ru.kpfu.itis.quiz.Res
import ru.kpfu.itis.quiz.feature.profile.domain.usecase.GetUserUseCase
import ru.kpfu.itis.quiz.feature.profile.presentation.mapper.mapResult
import ru.kpfu.itis.quiz.feature.profile.presentation.mapper.mapUser
import ru.kpfu.itis.quiz.feature.profile.presentation.mvi.other.OtherUserProfileScreenIntent
import ru.kpfu.itis.quiz.feature.profile.presentation.mvi.other.OtherUserProfileScreenSideEffect
import ru.kpfu.itis.quiz.feature.profile.presentation.mvi.other.OtherUserProfileScreenState

class OtherUserProfileViewModel(
    savedStateHandle: SavedStateHandle,
    private val getUserUseCase: GetUserUseCase,
): ViewModel(), ContainerHost<OtherUserProfileScreenState, OtherUserProfileScreenSideEffect> {

    override val container = container<OtherUserProfileScreenState, OtherUserProfileScreenSideEffect>(
        initialState = OtherUserProfileScreenState(),
        savedStateHandle = savedStateHandle,
        serializer = OtherUserProfileScreenState.serializer(),
    )

    fun onIntent(intent: OtherUserProfileScreenIntent) {
        when(intent) {
            is OtherUserProfileScreenIntent.GetUser -> getUser(intent)
        }
    }

    private fun getUser(intent: OtherUserProfileScreenIntent.GetUser) = intent {
        try {
            val user = getUserUseCase.invoke(intent.id)
            if (user != null) {
                reduce { state.copy(
                    user = mapUser(user.user),
                    results = user.results.map { mapResult(it) }
                ) }
            } else {
                postSideEffect(
                    OtherUserProfileScreenSideEffect.ShowError(
                        title = Res.string.incorrect_other_user_data,
                        message = Res.string.no_user,
                    )
                )
            }
        } catch (ex: Throwable) {
            postSideEffect(OtherUserProfileScreenSideEffect.ShowError(
                title = Res.string.incorrect_other_user_data,
                message = ex.message ?: Res.string.default_error_msg,
            ))
        }
    }
}

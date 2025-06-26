package ru.kpfu.itis.quiz.feature.profile.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import ru.kpfu.itis.quiz.Res
import ru.kpfu.itis.quiz.core.util.validatePassword
import ru.kpfu.itis.quiz.core.util.validateUsername
import ru.kpfu.itis.quiz.feature.profile.domain.usecase.GetCurrentUserUseCase
import ru.kpfu.itis.quiz.feature.profile.domain.usecase.LogoutUseCase
import ru.kpfu.itis.quiz.feature.profile.domain.usecase.settings.UpdateCredentialsUseCase
import ru.kpfu.itis.quiz.feature.profile.domain.usecase.settings.UpdateProfilePictureUseCase
import ru.kpfu.itis.quiz.feature.profile.domain.usecase.settings.UpdateUserInfoUseCase
import ru.kpfu.itis.quiz.feature.profile.presentation.mapper.mapResult
import ru.kpfu.itis.quiz.feature.profile.presentation.mapper.mapUser
import ru.kpfu.itis.quiz.feature.profile.presentation.mvi.profile.ProfileScreenIntent
import ru.kpfu.itis.quiz.feature.profile.presentation.mvi.profile.ProfileScreenSideEffect
import ru.kpfu.itis.quiz.feature.profile.presentation.mvi.profile.ProfileScreenState

class ProfileViewModel(
    savedStateHandle: SavedStateHandle,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val updateProfilePictureUseCase: UpdateProfilePictureUseCase,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase,
    private val updateCredentialsUseCase: UpdateCredentialsUseCase,
): ViewModel(), ContainerHost<ProfileScreenState, ProfileScreenSideEffect> {

    override val container = container<ProfileScreenState, ProfileScreenSideEffect>(
        initialState = ProfileScreenState(),
        savedStateHandle = savedStateHandle,
        serializer = ProfileScreenState.serializer()
    )

    fun onIntent(intent: ProfileScreenIntent) {
        when(intent) {
            is ProfileScreenIntent.GetCurrent -> getCurrentUser()
            is ProfileScreenIntent.UpdatePassword -> updatePassword(intent)
            is ProfileScreenIntent.UpdateProfilePicture -> updateProfilePicture(intent)
            is ProfileScreenIntent.UpdateUserInfo -> updateUserInfo(intent)
            is ProfileScreenIntent.ClearErrors -> clearErrors()
            is ProfileScreenIntent.ValidateUsername -> validateUsername(intent)
            is ProfileScreenIntent.ValidatePassword -> validatePassword(intent)
            is ProfileScreenIntent.ValidateConfirmPassword -> validateConfirmPassword(intent)
            is ProfileScreenIntent.Logout -> logout()
            is ProfileScreenIntent.ConfirmProfilePicture -> onProfilePictureChosen(intent)
        }
    }

    private fun getCurrentUser() = intent {
        try {
            val user = getCurrentUserUseCase.invoke()
            if (user != null) {
                reduce { state.copy(
                    user = mapUser(user.user),
                    results = user.results.map { mapResult(it) }
                ) }
            } else {
                postSideEffect(
                    ProfileScreenSideEffect.ShowError(
                        title = Res.string.incorrect_user_data,
                        message = Res.string.user_not_present,
                    )
                )
                postSideEffect(ProfileScreenSideEffect.GoToSignIn)
            }
        } catch (ex: Throwable) {
            postSideEffect(
                ProfileScreenSideEffect.ShowError(
                    title = Res.string.incorrect_user_data,
                    message = ex.message ?: Res.string.default_error_msg
                )
            )
        }
    }

    private fun updatePassword(intent: ProfileScreenIntent.UpdatePassword) = intent {
        reduce { state.copy(processingCredentials = true) }
        try {
            updateCredentialsUseCase.invoke(password = intent.password)
        } catch (ex: Throwable) {
            postSideEffect(
                ProfileScreenSideEffect.ShowError(
                    title = Res.string.dialog_incorrect_credentials,
                    message = ex.message ?: Res.string.default_error_msg
                )
            )
        }
        reduce { state.copy(processingCredentials = false) }
    }

    private fun updateProfilePicture(intent: ProfileScreenIntent.UpdateProfilePicture) = intent {
        try {
            updateProfilePictureUseCase.invoke(intent.uri)
            reduce { state.copy(user = state.user?.copy(profilePictureUri = intent.uri)) }
        } catch (ex: Throwable) {
            postSideEffect(
                ProfileScreenSideEffect.ShowError(
                    title = Res.string.save_photo_fail,
                    message = ex.message ?: Res.string.default_error_msg
                )
            )
        }
    }

    private fun onProfilePictureChosen(intent: ProfileScreenIntent.ConfirmProfilePicture) = intent {
        postSideEffect(ProfileScreenSideEffect.ProfilePictureConfirmed(intent.uri))
    }

    private fun updateUserInfo(intent: ProfileScreenIntent.UpdateUserInfo) = intent {
        try {
            updateUserInfoUseCase.invoke(
                username = intent.username,
                info = intent.info
            )
            reduce {
                state.copy(
                    user = state.user?.let { user ->
                        user.copy(
                            username = intent.username ?: user.username,
                            info = intent.info ?: user.info
                        )
                    }
                )
            }
        } catch (ex: Throwable) {
            postSideEffect(
                ProfileScreenSideEffect.ShowError(
                    title = Res.string.dialog_incorrect_user_data,
                    message = ex.message ?: Res.string.default_error_msg
                )
            )
        }
    }


    private fun logout() = intent {
        try {
            logoutUseCase.invoke()
            postSideEffect(ProfileScreenSideEffect.GoToSignIn)
        } catch (ex: Throwable) {
            postSideEffect(
                ProfileScreenSideEffect.ShowError(
                    title = Res.string.logout_unable,
                    message = ex.message ?: Res.string.logout_unable_info
                )
            )
        }
    }

    private fun validatePassword(intent: ProfileScreenIntent.ValidatePassword) = intent {
        val result = if (intent.password.isNullOrEmpty()) null
        else if (validatePassword(intent.password)) null
        else Res.string.password_requirements
        reduce { state.copy(passwordError = result) }
    }

    private fun validateConfirmPassword(intent: ProfileScreenIntent.ValidateConfirmPassword) = intent {
        val result = if (intent.confirmPassword.isNullOrEmpty()) null
        else if (validatePassword(intent.confirmPassword)) null
        else Res.string.password_requirements
        reduce { state.copy(confirmPasswordError = result) }
    }

    private fun validateUsername(intent: ProfileScreenIntent.ValidateUsername) = intent {
        val result = if (intent.username.isNullOrEmpty()) null
        else if (validateUsername(intent.username)) null
        else Res.string.username_requirements
        reduce { state.copy(usernameError = result) }
    }

    private fun clearErrors() = intent {
        reduce {
            state.copy(usernameError = null, passwordError = null,
                emailError = null, confirmPasswordError = null)
        }
    }
}

package ru.kpfu.itis.quiz.feature.authentication.presentation.register.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import ru.kpfu.itis.quiz.Res
import ru.kpfu.itis.quiz.core.util.validatePassword
import ru.kpfu.itis.quiz.core.util.validateUsername
import ru.kpfu.itis.quiz.feature.authentication.domain.usecase.GetSignedInUserUseCase
import ru.kpfu.itis.quiz.feature.authentication.domain.usecase.RegisterUserUseCase
import ru.kpfu.itis.quiz.feature.authentication.presentation.register.mvi.RegisterScreenIntent
import ru.kpfu.itis.quiz.feature.authentication.presentation.register.mvi.RegisterScreenSideEffect
import ru.kpfu.itis.quiz.feature.authentication.presentation.register.mvi.RegisterScreenState

class RegisterViewModel(
    savedStateHandle: SavedStateHandle,
    private val registerUserUseCase: RegisterUserUseCase,
    private val getSignedInUserUseCase: GetSignedInUserUseCase,
): ViewModel(), ContainerHost<RegisterScreenState, RegisterScreenSideEffect> {

    override val container = container<RegisterScreenState, RegisterScreenSideEffect>(
        initialState = RegisterScreenState(),
        savedStateHandle = savedStateHandle,
        serializer = RegisterScreenState.serializer()
    )

    fun onIntent(intent: RegisterScreenIntent) {
        when (intent) {
            is RegisterScreenIntent.GetSignedInUser -> getSignedInUser()
            is RegisterScreenIntent.RegisterUser -> registerUser(intent)
            is RegisterScreenIntent.ValidatePassword -> validatePassword(intent)
            is RegisterScreenIntent.ValidateConfirmPassword -> validateConfirmPassword(intent)
            is RegisterScreenIntent.ValidateUsername -> validateUsername(intent)
        }
    }

    private fun registerUser(intent: RegisterScreenIntent.RegisterUser) = intent {
        reduce { state.copy(isLoading = true) }

        try {
            val isSuccess = registerUserUseCase.invoke(
                username = intent.username,
                password = intent.password,
                confirmPassword = intent.confirmPassword
            )

            if (isSuccess) {
                reduce { state.copy(username = intent.username, isLoading = false) }
                postSideEffect(RegisterScreenSideEffect.NavigateToMainMenu)
            } else {
                reduce { state.copy(username = "", isLoading = false) }
                postSideEffect(RegisterScreenSideEffect.ShowError(
                    title = Res.string.register_fail,
                    message = Res.string.register_fail_invalid
                ))
            }
        } catch (ex: Throwable) {
            reduce { state.copy(username = "", isLoading = false) }
            postSideEffect(RegisterScreenSideEffect.ShowError(
                title = Res.string.register_fail,
                message = ex.message ?: Res.string.default_error_msg
            ))
        }
    }

    private fun getSignedInUser() = intent {
        try {
            val user = getSignedInUserUseCase.invoke()
            user?.let {
                reduce { state.copy(username = it.username) }
                postSideEffect(RegisterScreenSideEffect.NavigateToMainMenu)
            }
        } catch (ex: Throwable) {
            reduce { state.copy(username = "") }
        }
    }

    private fun validatePassword(intent: RegisterScreenIntent.ValidatePassword) = intent {
        reduce { state.copy(
            passwordError = if (validatePassword(intent.password)) null else Res.string.password_requirements
        ) }
    }

    private fun validateConfirmPassword(intent: RegisterScreenIntent.ValidateConfirmPassword) = intent {
        reduce { state.copy(
            confirmPasswordError = if (validatePassword(intent.password)) null else Res.string.password_requirements
        ) }
    }

    private fun validateUsername(intent: RegisterScreenIntent.ValidateUsername) = intent {
        reduce { state.copy(
            usernameError = if (validateUsername(intent.username)) null else Res.string.username_requirements
        ) }
    }
}

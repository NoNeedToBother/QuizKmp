package ru.kpfu.itis.quiz.feature.authentication.presentation.register.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
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

            reduce { state.copy(username = intent.username, isLoading = false) }
            postSideEffect(RegisterScreenSideEffect.NavigateToMainMenu)
        } catch (ex: Throwable) {
            reduce { state.copy(username = "", isLoading = false) }
            /*postSideEffect(RegisterScreenSideEffect.ShowError(
                title = resourceManager.getString(R.string.registration_failed),
                message = ex.message ?: resourceManager.getString(R.string.default_error_msg))
            )*/
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
            /*postSideEffect(RegisterScreenSideEffect.ShowError(
                title = resourceManager.getString(R.string.sign_in_failed),
                message = ex.message ?: resourceManager.getString(R.string.default_error_msg))
            )*/
        }
    }

    private fun validatePassword(intent: RegisterScreenIntent.ValidatePassword) = intent {
        /*val result = if (passwordValidator.validate(password)) null
        else resourceManager.getString(ru.kpfu.itis.paramonov.core.R.string.weak_password_msg)
        reduce { state.copy(passwordError = result) }*/
    }

    private fun validateConfirmPassword(intent: RegisterScreenIntent.ValidateConfirmPassword) = intent {
        /*val result = if (passwordValidator.validate(password)) null
        else resourceManager.getString(ru.kpfu.itis.paramonov.core.R.string.weak_password_msg)
        reduce { state.copy(confirmPasswordError = result) }*/
    }

    private fun validateUsername(intent: RegisterScreenIntent.ValidateUsername) = intent {
        /*val result = if (usernameValidator.validate(username)) null
        else resourceManager.getString(ru.kpfu.itis.paramonov.core.R.string.invalid_username_msg)
        reduce { state.copy(usernameError = result) }*/
    }
}

package ru.kpfu.itis.quiz.feature.authentication.presentation.signin.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import ru.kpfu.itis.quiz.core.util.passwordRequirements
import ru.kpfu.itis.quiz.core.util.validatePassword
import ru.kpfu.itis.quiz.feature.authentication.domain.usecase.GetSignedInUserUseCase
import ru.kpfu.itis.quiz.feature.authentication.domain.usecase.SignInUserUseCase
import ru.kpfu.itis.quiz.feature.authentication.presentation.signin.mvi.SignInScreenIntent
import ru.kpfu.itis.quiz.feature.authentication.presentation.signin.mvi.SignInScreenSideEffect
import ru.kpfu.itis.quiz.feature.authentication.presentation.signin.mvi.SignInScreenState

class SignInViewModel(
    savedStateHandle: SavedStateHandle,
    private val signInUserUseCase: SignInUserUseCase,
    private val getSignedInUserUseCase: GetSignedInUserUseCase,
): ViewModel(), ContainerHost<SignInScreenState, SignInScreenSideEffect> {

    override val container = container<SignInScreenState, SignInScreenSideEffect>(
        initialState = SignInScreenState(),
        savedStateHandle = savedStateHandle,
        serializer = SignInScreenState.serializer()
    )

    fun onIntent(intent: SignInScreenIntent) = intent {
        when(intent) {
            is SignInScreenIntent.AuthenticateUser -> authenticateUser(intent)
            SignInScreenIntent.GetSignedInUser -> getSignedInUser()
            is SignInScreenIntent.ValidatePassword -> validatePassword(intent)
            is SignInScreenIntent.ValidateUsername -> validateUsername(intent)
        }
    }

    private fun authenticateUser(intent: SignInScreenIntent.AuthenticateUser) = intent {
        reduce { state.copy(isLoading = true) }

        try {
            val isSuccess = signInUserUseCase.invoke(intent.username, intent.password)

            if (isSuccess) {
                reduce { state.copy(username = intent.username, isLoading = false) }
                postSideEffect(SignInScreenSideEffect.NavigateToMainMenu)
            } else {
                reduce { state.copy(isLoading = false) }
            }
        } catch (ex: Throwable) {
            reduce { state.copy(username = "", isLoading = false) }
            postSideEffect(
                SignInScreenSideEffect.ShowError(
                title = "Sign In failed",
                message = ex.message ?: "Something bad happened..."
                //title = resourceManager.getString(R.string.sign_in_failed),
                //message = ex.message ?: resourceManager.getString(R.string.default_error_msg)
            ))
        }
    }

    private fun getSignedInUser() = intent {
        try {
            val user = getSignedInUserUseCase.invoke()
            user?.let {
                reduce { state.copy(username = it.username) }
                postSideEffect(SignInScreenSideEffect.NavigateToMainMenu)
            }
        } catch (ex: Throwable) {
            reduce { state.copy(username = "") }
        }
    }

    private fun validatePassword(intent: SignInScreenIntent.ValidatePassword) = intent {
        val result: String? = if (validatePassword(intent.password)) null else passwordRequirements
        reduce { state.copy(passwordError = result) }
    }

    private fun validateUsername(intent: SignInScreenIntent.ValidateUsername) = intent {
        //val result = if (email.contains("@")) null
        //else resourceManager.getString(ru.kpfu.itis.paramonov.core.R.string.invalid_email)
        //reduce { state.copy(emailError = result) }
    }
}

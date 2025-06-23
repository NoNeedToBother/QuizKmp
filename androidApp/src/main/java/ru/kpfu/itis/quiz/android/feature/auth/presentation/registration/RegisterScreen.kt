package ru.kpfu.itis.quiz.android.feature.auth.presentation.registration

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import ru.kpfu.itis.quiz.android.R
import ru.kpfu.itis.quiz.android.feature.auth.presentation.components.Logo
import ru.kpfu.itis.quiz.android.core.designsystem.components.ErrorDialog
import ru.kpfu.itis.quiz.android.core.designsystem.components.InputSection
import ru.kpfu.itis.quiz.android.core.designsystem.components.PasswordInputSection
import ru.kpfu.itis.quiz.feature.authentication.presentation.register.mvi.RegisterScreenIntent
import ru.kpfu.itis.quiz.feature.authentication.presentation.register.mvi.RegisterScreenSideEffect
import ru.kpfu.itis.quiz.feature.authentication.presentation.register.mvi.RegisterScreenState
import ru.kpfu.itis.quiz.feature.authentication.presentation.register.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(
    goToSignInScreen: () -> Unit,
    goToMainMenuScreen: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel()
) {

    val state = viewModel.container.stateFlow.collectAsState()
    val effect = viewModel.container.sideEffectFlow

    var error by remember { mutableStateOf<Pair<String, String>?>(null) }

    LaunchedEffect(null) {
        viewModel.onIntent(RegisterScreenIntent.GetSignedInUser)

        effect.collect {
            when (it) {
                is RegisterScreenSideEffect.NavigateToMainMenu -> {
                    goToMainMenuScreen()
                }
                is RegisterScreenSideEffect.ShowError -> {
                    val errorMessage = it.message
                    val errorTitle = it.title
                    error = errorTitle to errorMessage
                }
            }
        }
    }

    ScreenContent(
        modifier = Modifier.fillMaxSize(),
        state = state,
        onUsernameInput = { viewModel.onIntent(RegisterScreenIntent.ValidateUsername(it)) },
        onConfirmPasswordInput = { viewModel.onIntent(RegisterScreenIntent.ValidateConfirmPassword(it)) },
        onPasswordInput = { viewModel.onIntent(RegisterScreenIntent.ValidatePassword(it)) },
        onRegisterBtnClick = { username, password, confirmPassword ->
            viewModel.onIntent(RegisterScreenIntent.RegisterUser(username, password, confirmPassword))
        },
        onGoToSignInClick = { goToSignInScreen() }
    )

    Box {
        error?.let {
            ErrorDialog(
                onDismiss = { error = null },
                title = it.first,
                text = it.second
            )
        }
    }
}

@Composable
fun ScreenContent(
    modifier: Modifier = Modifier,
    state: State<RegisterScreenState>,
    onUsernameInput: (String) -> Unit,
    onConfirmPasswordInput: (String) -> Unit,
    onPasswordInput: (String) -> Unit,
    onRegisterBtnClick: (username: String, password: String, confirmPassword: String) -> Unit,
    onGoToSignInClick: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 64.dp, vertical = 12.dp)
                .align(Alignment.TopCenter),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Logo()
            InputSection(
                value = username,
                onInput = {
                    username = it
                    onUsernameInput.invoke(username)
                },
                label = stringResource(R.string.enter_username),
                error = state.value.usernameError
            )

            Spacer(modifier = Modifier.height(16.dp))

            PasswordInputSection(
                value = password,
                onInput = {
                    password = it
                    onPasswordInput.invoke(password)
                },
                label = stringResource(R.string.enter_password),
                error = state.value.passwordError
            )

            Spacer(modifier = Modifier.height(16.dp))

            PasswordInputSection(
                value = confirmPassword,
                onInput = {
                    confirmPassword = it
                    onConfirmPasswordInput.invoke(confirmPassword)
                },
                label = stringResource(R.string.confirm_password),
                error = state.value.confirmPasswordError
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(80.dp)
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    onRegisterBtnClick.invoke(
                        username, password, confirmPassword
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = state.value.passwordError == null && state.value.usernameError == null &&
                        state.value.confirmPasswordError == null &&
                        username.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()
            ) {
                Text(stringResource(R.string.register))
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { onGoToSignInClick() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.go_to_sign_in))
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

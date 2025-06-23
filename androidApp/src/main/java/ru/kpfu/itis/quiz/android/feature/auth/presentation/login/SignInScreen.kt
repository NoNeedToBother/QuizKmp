package ru.kpfu.itis.quiz.android.feature.auth.presentation.login

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import ru.kpfu.itis.quiz.android.R
import ru.kpfu.itis.quiz.android.feature.auth.presentation.components.Logo
import ru.kpfu.itis.quiz.android.core.designsystem.components.ErrorDialog
import ru.kpfu.itis.quiz.android.core.designsystem.components.InputSection
import ru.kpfu.itis.quiz.android.core.designsystem.components.PasswordInputSection
import ru.kpfu.itis.quiz.feature.authentication.presentation.signin.mvi.SignInScreenIntent
import ru.kpfu.itis.quiz.feature.authentication.presentation.signin.mvi.SignInScreenSideEffect
import ru.kpfu.itis.quiz.feature.authentication.presentation.signin.mvi.SignInScreenState
import ru.kpfu.itis.quiz.feature.authentication.presentation.signin.viewmodel.SignInViewModel

@Composable
fun SignInScreen(
    goToRegisterScreen: () -> Unit,
    goToMainMenuScreen: () -> Unit,
    viewModel: SignInViewModel = koinViewModel()
) {

    val state = viewModel.container.stateFlow.collectAsState()
    val effect = viewModel.container.sideEffectFlow
    var showToast by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<Pair<String, String>?>(null) }

    LaunchedEffect(null) {
        viewModel.onIntent(SignInScreenIntent.GetSignedInUser)

        effect.collect {
            when (it) {
                is SignInScreenSideEffect.NavigateToMainMenu -> {
                    showToast = true
                    goToMainMenuScreen()
                }
                is SignInScreenSideEffect.ShowError -> {
                    val errorMessage = it.message
                    val errorTitle = it.title

                    error = errorTitle to errorMessage
                }
            }
        }
    }

    ScreenContent(
        state = state,
        onUsernameInput = { viewModel.onIntent(SignInScreenIntent.ValidateUsername(it)) },
        onPasswordInput = { viewModel.onIntent(SignInScreenIntent.ValidatePassword(it)) },
        onSignInBtnClick = { email, password ->
            viewModel.onIntent(
                SignInScreenIntent.AuthenticateUser(email, password)
            )
        },
        onGoToRegisterClick = { goToRegisterScreen() }
    )

    if (showToast) {
        Toast.makeText(LocalContext.current,
            stringResource(R.string.welcome_back_user, state.value.username),
            Toast.LENGTH_LONG).show()
        showToast = false
    }

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
    state: State<SignInScreenState>,
    onUsernameInput: (String) -> Unit,
    onPasswordInput: (String) -> Unit,
    onSignInBtnClick: (String, String) -> Unit,
    onGoToRegisterClick: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box {
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
                    onSignInBtnClick.invoke(username, password)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = state.value.passwordError == null && password.isNotEmpty() &&
                        state.value.usernameError == null && username.isNotEmpty()
            ) {
                Text(stringResource(R.string.sign_in))
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { onGoToRegisterClick() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.go_to_register))
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

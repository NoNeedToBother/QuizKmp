package ru.kpfu.itis.quiz.authentication.presentation.login

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
import org.kodein.di.compose.localDI
import org.kodein.di.instance
import ru.kpfu.itis.quiz.android.R
import ru.kpfu.itis.quiz.authentication.presentation.components.Logo
import ru.kpfu.itis.quiz.authentication.presentation.login.mvi.SignInScreenSideEffect
import ru.kpfu.itis.quiz.authentication.presentation.login.mvi.SignInScreenState
import ru.kpfu.itis.quiz.core.designsystem.components.ErrorDialog
import ru.kpfu.itis.quiz.core.designsystem.components.InputSection
import ru.kpfu.itis.quiz.core.designsystem.components.PasswordInputSection

@Composable
fun SignInScreen(
    goToRegisterScreen: () -> Unit,
    goToMainMenuScreen: () -> Unit
) {
    val di = localDI()
    val viewModel: SignInViewModel by di.instance()

    val state = viewModel.container.stateFlow.collectAsState()
    val effect = viewModel.container.sideEffectFlow
    var showToast by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<Pair<String, String>?>(null) }

    LaunchedEffect(null) {
        viewModel.checkCurrentUser()

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
        onEmailInput = { viewModel.validateEmail(it) },
        onPasswordInput = { viewModel.validatePassword(it) },
        onSignInBtnClick = { email, password ->
            viewModel.authenticateUser(email, password)
        },
        onGoToRegisterClick = { goToRegisterScreen() }
    )

    if (showToast) {
        Toast.makeText(LocalContext.current,
            stringResource(R.string.welcome_back_user, state.value.userData?.username ?: ""),
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
    onEmailInput: (String) -> Unit,
    onPasswordInput: (String) -> Unit,
    onSignInBtnClick: (String, String) -> Unit,
    onGoToRegisterClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
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
                value = email,
                onInput = {
                    email = it
                    onEmailInput.invoke(email)
                },
                label = stringResource(R.string.enter_email),
                error = state.value.emailError
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
                    onSignInBtnClick.invoke(email, password)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = state.value.passwordError != null && password.isNotEmpty()
                        && state.value.emailError != null && email.isNotEmpty()
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

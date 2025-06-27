package ru.kpfu.itis.quiz.android.feature.profile.presentation.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import ru.kpfu.itis.quiz.android.R
import ru.kpfu.itis.quiz.android.core.designsystem.components.ErrorDialog
import ru.kpfu.itis.quiz.android.core.designsystem.components.InfoTextField
import ru.kpfu.itis.quiz.android.core.designsystem.components.ProfilePicture
import ru.kpfu.itis.quiz.android.core.designsystem.components.TextButton
import ru.kpfu.itis.quiz.feature.profile.presentation.mvi.other.OtherUserProfileScreenState
import ru.kpfu.itis.quiz.android.feature.profile.presentation.ui.screens.dialogs.StatsDialog
import ru.kpfu.itis.quiz.feature.profile.presentation.mvi.other.OtherUserProfileScreenIntent
import ru.kpfu.itis.quiz.feature.profile.presentation.mvi.other.OtherUserProfileScreenSideEffect
import ru.kpfu.itis.quiz.feature.profile.presentation.viewmodel.OtherUserProfileViewModel

@Composable
fun OtherUserProfileScreen(
    userId: Long,
    viewModel: OtherUserProfileViewModel = koinViewModel()
) {
    val state = viewModel.container.stateFlow.collectAsState()
    val effect = viewModel.container.sideEffectFlow

    var error by remember { mutableStateOf<Pair<String, String>?>(null) }

    var showStatsDialog by remember { mutableStateOf(false) }

    LaunchedEffect(null) {
        viewModel.onIntent(OtherUserProfileScreenIntent.GetUser(userId))

        effect.collect {
            when (it) {
                is OtherUserProfileScreenSideEffect.ShowError -> {
                    val errorMessage = it.message
                    val errorTitle = it.title

                    error = errorTitle to errorMessage
                }
            }
        }
    }

    ScreenContent(
        modifier = Modifier.fillMaxSize(),
        state = state.value,
        onGetResultsClicked = { showStatsDialog = true },
    ) {
        if (showStatsDialog) {
            StatsDialog(
                results = state.value.results,
                onDismiss = { showStatsDialog = false }
            )
        }
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
    modifier: Modifier = Modifier,
    state: OtherUserProfileScreenState,
    onGetResultsClicked: () -> Unit,
    dialogs: @Composable () -> Unit
) {
    Column(
        modifier = modifier.padding(PaddingValues(top = 24.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfilePicture(
            uri = state.user?.profilePictureUri ?: "",
            modifier = Modifier.size(200.dp)
        )
        InfoTextField(
            modifier = Modifier.padding(PaddingValues(top = 12.dp, start = 80.dp, end = 80.dp)),
            label = stringResource(R.string.username),
            value = state.user?.username ?: ""
        )
        InfoTextField(
            modifier = Modifier.padding(PaddingValues(top = 12.dp, start = 80.dp, end = 80.dp)),
            label = stringResource(R.string.info),
            value = state.user?.info ?: ""
        )
        InfoTextField(
            modifier = Modifier.padding(PaddingValues(top = 12.dp, start = 80.dp, end = 80.dp)),
            label = stringResource(R.string.registration_date, state.user?.dateRegistered ?: ""),
            value = ""
        )
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextButton(
                onClick = { onGetResultsClicked() },
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 80.dp)
                    .padding(bottom = 12.dp),
                text = stringResource(R.string.check_stats),
            )
        }
    }
    dialogs()
}

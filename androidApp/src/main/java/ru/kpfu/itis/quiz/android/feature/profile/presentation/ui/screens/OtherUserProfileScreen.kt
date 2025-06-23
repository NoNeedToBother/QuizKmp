package ru.kpfu.itis.quiz.android.feature.profile.presentation.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import ru.kpfu.itis.quiz.android.R
import ru.kpfu.itis.quiz.core.model.Result
import ru.kpfu.itis.quiz.android.feature.profile.presentation.ui.screens.dialogs.StatsDialog
import ru.kpfu.itis.quiz.android.feature.profile.presentation.mvi.OtherUserProfileScreenSideEffect
import ru.kpfu.itis.quiz.android.feature.profile.presentation.mvi.OtherUserProfileScreenState
import ru.kpfu.itis.quiz.android.feature.profile.presentation.ui.components.ProfileInfoField
import ru.kpfu.itis.quiz.android.core.designsystem.components.ErrorDialog

private const val MAX_RESULTS_AMOUNT = 10

@Composable
fun OtherUserProfileScreen(
    userId: Long
) {
    /*val di = localDI()
    val viewModel: OtherUserProfileViewModel by di.instance()
    val state = viewModel.container.stateFlow.collectAsState()
    val effect = viewModel.container.sideEffectFlow

    var results by remember { mutableStateOf<List<Result>?>(null) }
    var error by remember { mutableStateOf<Pair<String, String>?>(null) }

    LaunchedEffect(null) {
        viewModel.getUser(userId)
        viewModel.checkFriendStatus(userId)

        effect.collect {
            when (it) {
                is OtherUserProfileScreenSideEffect.ShowError -> {
                    val errorMessage = it.message
                    val errorTitle = it.title

                    error = errorTitle to errorMessage
                }
                is OtherUserProfileScreenSideEffect.ResultsReceived ->
                    results = it.results
            }
        }
    }

    ScreenContent(
        modifier = Modifier.fillMaxSize(),
        state = state,
        onGetResultsClicked = { viewModel.getLastResults(MAX_RESULTS_AMOUNT, userId) },
        onAddFriendClick = { viewModel.sendFriendRequest(userId) }
    ) {
        results?.let {
            StatsDialog(
                results = it,
                onDismiss = {
                    results = null
                }
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
    }*/
}

@Composable
fun ScreenContent(
    modifier: Modifier = Modifier,
    state: State<OtherUserProfileScreenState>,
    onGetResultsClicked: () -> Unit,
    dialogs: @Composable () -> Unit
) {
    Column(
        modifier = modifier.padding(PaddingValues(top = 24.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(state.value.user?.profilePictureUri),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(200.dp).clip(CircleShape)
        )
        ProfileInfoField(
            modifier = Modifier.padding(PaddingValues(top = 12.dp, start = 80.dp, end = 80.dp)),
            label = stringResource(R.string.username),
            value = state.value.user?.username ?: ""
        )
        ProfileInfoField(
            modifier = Modifier.padding(PaddingValues(top = 12.dp, start = 80.dp, end = 80.dp)),
            label = stringResource(R.string.info),
            value = state.value.user?.info ?: ""
        )
        ProfileInfoField(
            modifier = Modifier.padding(PaddingValues(top = 12.dp, start = 80.dp, end = 80.dp)),
            label = stringResource(R.string.registration_date, state.value.user?.dateRegistered ?: ""),
            value = ""
        )
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { onGetResultsClicked() },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 80.dp)
            ) {
                Text(text = stringResource(R.string.check_stats))
            }
        }
    }
    dialogs()
}

package ru.kpfu.itis.quiz.android.feature.leaderboard.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import org.koin.compose.viewmodel.koinViewModel
import ru.kpfu.itis.quiz.android.R
import ru.kpfu.itis.quiz.android.core.designsystem.components.ErrorDialog
import ru.kpfu.itis.quiz.android.feature.leaderboard.presentation.ui.components.ResultList
import ru.kpfu.itis.quiz.android.feature.leaderboard.presentation.ui.components.SettingsBottomSheetWrapper
import ru.kpfu.itis.quiz.core.util.normalizeEnumName
import ru.kpfu.itis.quiz.feature.leaderboard.presentation.mvi.LeaderboardScreenIntent
import ru.kpfu.itis.quiz.feature.leaderboard.presentation.mvi.LeaderboardScreenSideEffect
import ru.kpfu.itis.quiz.feature.leaderboard.presentation.mvi.LeaderboardScreenState
import ru.kpfu.itis.quiz.feature.leaderboard.presentation.viewmodel.LeaderboardViewModel

private const val LEADERBOARD_MAX_AT_ONCE = 15

@Composable
fun LeaderboardsScreen(
    goToUserScreen: (Long) -> Unit,
    viewModel: LeaderboardViewModel = koinViewModel()
) {
    val state = viewModel.container.stateFlow.collectAsState()
    val effect = viewModel.container.sideEffectFlow

    var error by remember { mutableStateOf<Pair<String, String>?>(null) }

    LaunchedEffect(null) {
        viewModel.onIntent(LeaderboardScreenIntent.GetLeaderboard(LEADERBOARD_MAX_AT_ONCE))

        effect.collect {
            when (it) {
                is LeaderboardScreenSideEffect.ShowError -> {
                    val errorMessage = it.message
                    val errorTitle = it.title

                    error = errorTitle to errorMessage
                }
            }
        }
    }

    ScreenContent(
        state = state.value,
        onProfileClick = { goToUserScreen(it) },
        onSaveClick = {
            viewModel.onIntent(LeaderboardScreenIntent.SaveSettings)
            viewModel.onIntent(LeaderboardScreenIntent.GetLeaderboard(LEADERBOARD_MAX_AT_ONCE))
        },
        onDifficultyChosen = { viewModel.onIntent(LeaderboardScreenIntent.ChangeDifficulty(it)) },
        onCategoryChosen = { viewModel.onIntent(LeaderboardScreenIntent.ChangeCategory(it)) },
        onGameModeChosen = { viewModel.onIntent(LeaderboardScreenIntent.ChangeGameMode(it)) }
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
    state: LeaderboardScreenState,
    onProfileClick: (Long) -> Unit,
    onSaveClick: () -> Unit,
    onDifficultyChosen: (String) -> Unit,
    onCategoryChosen: (String) -> Unit,
    onGameModeChosen: (String) -> Unit
) {
    SettingsBottomSheetWrapper(
        onSaveClick = {
            onSaveClick()
        },
        onDifficultyChosen = onDifficultyChosen,
        onCategoryChosen = onCategoryChosen,
        onGameModeChosen = onGameModeChosen,
        difficultyValue = state.settings?.difficulty?.toString()?.normalizeEnumName()
            ?: stringResource(R.string.any_settings_item),
        categoryValue = state.settings?.category?.toString()?.normalizeEnumName()
            ?: stringResource(R.string.any_settings_item),
        gameModeValue = state.settings?.gameMode.toString().normalizeEnumName(),
    ) { paddingValues ->
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
        ) {
            Leaderboard(
                state = state,
                onProfileClick = onProfileClick,
            )
        }
    }
}

@Composable
fun Leaderboard(
    state: LeaderboardScreenState,
    onProfileClick: (Long) -> Unit,
) {
    Box {
        ResultList(
            modifier = Modifier.align(Alignment.TopCenter),
            results = state.results,
            onProfileClick = onProfileClick,
        )
    }
}

package ru.kpfu.itis.quiz.android.feature.questions.presentation.settings.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import ru.kpfu.itis.quiz.core.util.normalizeEnumName
import ru.kpfu.itis.quiz.android.R
import ru.kpfu.itis.quiz.feature.questions.presentation.settings.mvi.QuestionSettingsScreenState
import ru.kpfu.itis.quiz.android.core.designsystem.components.DropdownMenu
import ru.kpfu.itis.quiz.android.core.designsystem.components.ErrorDialog
import ru.kpfu.itis.quiz.android.core.designsystem.components.TextButton
import ru.kpfu.itis.quiz.feature.questions.presentation.settings.model.QuestionSettings
import ru.kpfu.itis.quiz.feature.questions.presentation.settings.mvi.QuestionSettingsScreenIntent
import ru.kpfu.itis.quiz.feature.questions.presentation.settings.mvi.QuestionSettingsScreenSideEffect
import ru.kpfu.itis.quiz.feature.questions.presentation.settings.viewmodel.QuestionSettingsViewModel

@Composable
fun QuestionSettingsScreen(
    viewModel: QuestionSettingsViewModel = koinViewModel()
) {
    val state = viewModel.container.stateFlow.collectAsState()
    val effect = viewModel.container.sideEffectFlow

    var error by remember { mutableStateOf<Pair<String, String>?>(null) }

    LaunchedEffect(Unit) {
        viewModel.onIntent(QuestionSettingsScreenIntent.GetQuestionSettings)

        effect.collect {
            when(it) {
                is QuestionSettingsScreenSideEffect.ShowError -> {
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
        onCategoryChosen = { viewModel.onIntent(QuestionSettingsScreenIntent.UpdateCategory(it)) },
        onDifficultyChosen = { viewModel.onIntent(QuestionSettingsScreenIntent.UpdateDifficulty(it)) },
        onGameModeChosen = { viewModel.onIntent(QuestionSettingsScreenIntent.UpdateGameMode(it)) },
        onSaveSettingsClick = { viewModel.onIntent(QuestionSettingsScreenIntent.SaveQuestionSettings) },
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
    state: QuestionSettingsScreenState,
    onCategoryChosen: (String) -> Unit,
    onDifficultyChosen: (String) -> Unit,
    onGameModeChosen: (String) -> Unit,
    onSaveSettingsClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Settings(
            settings = state.settings,
            onCategoryChosen = onCategoryChosen,
            onDifficultyChosen = onDifficultyChosen,
            onGameModeChosen = onGameModeChosen,
            onSaveClick = onSaveSettingsClick
        )
    }
}

@Composable
fun Settings(
    settings: QuestionSettings?,
    onCategoryChosen: (String) -> Unit,
    onDifficultyChosen: (String) -> Unit,
    onGameModeChosen: (String) -> Unit,
    onSaveClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 24.dp)
    ) {
        DropdownMenu(
            value = settings?.category?.toString()?.normalizeEnumName() ?: "",
            suggestions = stringArrayResource(R.array.categories).toList(),
            label = stringResource(R.string.category),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp),
            onChosen = onCategoryChosen
        )

        DropdownMenu(
            value = settings?.difficulty?.toString()?.normalizeEnumName() ?: "",
            suggestions = stringArrayResource(R.array.difficulties).toList(),
            label = stringResource(R.string.difficulty),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp),
            onChosen = onDifficultyChosen
        )

        DropdownMenu(
            value = settings?.gameMode?.toString()?.normalizeEnumName() ?: "",
            suggestions = stringArrayResource(R.array.game_modes).toList(),
            label = stringResource(R.string.game_mode),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp),
            onChosen = onGameModeChosen
        )

        Spacer(modifier = Modifier.weight(1f))

        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
                .padding(horizontal = 64.dp),
            onClick = onSaveClick,
            text = stringResource(R.string.save),
        )
    }
}

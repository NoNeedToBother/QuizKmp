package ru.kpfu.itis.quiz.android.feature.questions.presentation.settings.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import ru.kpfu.itis.quiz.core.util.normalizeEnumName
import ru.kpfu.itis.quiz.android.R
import ru.kpfu.itis.quiz.android.feature.questions.presentation.settings.mvi.QuestionSettingsScreenSideEffect
import ru.kpfu.itis.quiz.android.feature.questions.presentation.settings.mvi.QuestionSettingsScreenState
//import ru.kpfu.itis.quiz.questions.presentation.settings.viewmodel.QuestionSettingsViewModel
import ru.kpfu.itis.quiz.android.core.designsystem.components.DropdownMenu
import ru.kpfu.itis.quiz.android.core.designsystem.components.ErrorDialog
import ru.kpfu.itis.quiz.android.feature.questions.presentation.model.QuestionSettings

@Composable
fun QuestionSettingsScreen() {
    /*val di = localDI()
    val viewModel: QuestionSettingsViewModel by di.instance()

    val state = viewModel.container.stateFlow.collectAsState()
    val effect = viewModel.container.sideEffectFlow

    var error by remember { mutableStateOf<Pair<String, String>?>(null) }

    LaunchedEffect(Unit) {
        viewModel.getQuestionSettings()
        viewModel.getTrainingQuestionSettings()

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
        onCategoryChosen = { viewModel.updateCategory(it) },
        onDifficultyChosen = { viewModel.updateDifficulty(it) },
        onGameModeChosen = { viewModel.updateGameMode(it) },
        onSaveSettingsClick = { viewModel.saveQuestionSettings() },
    )

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
        SettingsTab(
            settings = state.settings,
            onCategoryChosen = onCategoryChosen,
            onDifficultyChosen = onDifficultyChosen,
            onGameModeChosen = onGameModeChosen,
            onSaveClick = onSaveSettingsClick
        )
    }
}

@Composable
fun SettingsTab(
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

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 100.dp)
                .padding(horizontal = 64.dp),
            onClick = onSaveClick
        ) {
            Text(text = stringResource(R.string.save))
        }
    }
}

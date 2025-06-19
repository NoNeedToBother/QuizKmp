package ru.kpfu.itis.quiz.questions.presentation.settings.ui.screens

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
import org.kodein.di.compose.localDI
import org.kodein.di.instance
import ru.kpfu.itis.quiz.core.utils.normalizeEnumName
import ru.kpfu.itis.quiz.android.R
import ru.kpfu.itis.quiz.questions.presentation.settings.mvi.QuestionSettingsScreenSideEffect
import ru.kpfu.itis.quiz.questions.presentation.settings.mvi.QuestionSettingsScreenState
import ru.kpfu.itis.quiz.questions.presentation.settings.viewmodel.QuestionSettingsViewModel
import ru.kpfu.itis.quiz.core.designsystem.components.DropdownMenu
import ru.kpfu.itis.quiz.core.designsystem.components.ErrorDialog

@Composable
fun QuestionSettingsScreen() {
    val di = localDI()
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
        onLimitChanged = { viewModel.updateLimit(it) },
        onSaveClick = { viewModel.saveTrainingQuestionSettings() },
        checkLimit = { viewModel.checkLimit(it) }
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
    onLimitChanged: (Int) -> Unit,
    onSaveClick: () -> Unit,
    checkLimit: (Int?) -> String?
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        stringResource(R.string.standard_settings),
        stringResource(R.string.training_settings)
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index }
                )
            }
        }

        when (selectedTabIndex) {
            0 -> SettingsTab(
                settings = state.settings,
                onCategoryChosen = onCategoryChosen,
                onDifficultyChosen = onDifficultyChosen,
                onGameModeChosen = onGameModeChosen,
                onSaveClick = onSaveSettingsClick
            )

            1 -> TrainingSettingsTab(
                settings = state.trainingSettings,
                onLimitChanged = onLimitChanged,
                onSaveClick = onSaveClick,
                checkLimit = checkLimit
            )
        }
    }
}

@Composable
fun SettingsTab(
    settings: QuestionSettingsUiModel?,
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

@Composable
fun TrainingSettingsTab(
    settings: TrainingQuestionSettingsUiModel?,
    onLimitChanged: (Int) -> Unit,
    onSaveClick: () -> Unit,
    checkLimit: (Int?) -> String?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 24.dp)
    ) {
        LimitInputSection(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            label = stringResource(R.string.enter_limit),
            value = settings?.limit.toString(),
            onInput = onLimitChanged,
            checkLimit = checkLimit
        )
        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 100.dp)
                .padding(horizontal = 64.dp),
            onClick = onSaveClick,
            enabled = settings?.let { it.limit > 0 } ?: false
        ) {
            Text(text = stringResource(R.string.save))
        }
    }
}

@Composable
fun LimitInputSection(
    modifier: Modifier = Modifier,
    label: String,
    value: String = "",
    onInput: (Int) -> Unit,
    checkLimit: (Int?) -> String?
) {
    var limitError by remember { mutableStateOf<String?>(null) }
    var limit by remember { mutableStateOf(value) }

    LaunchedEffect(value) {
        limit = value
    }

    OutlinedTextField(
        value = limit,
        onValueChange = {
            limit = it
            val inputToCheck: Int? = if (limit.isEmpty() || !limit.isDigitsOnly()) null else limit.toInt()
            val err = checkLimit(inputToCheck)
            limitError = err
            if (err == null) onInput(it.toInt())
        },
        label = { Text(label) },
        modifier = modifier,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        isError = limitError != null,
        supportingText = { limitError?.let { err -> Text(text = err) } }
    )
}

package ru.kpfu.itis.quiz.feature.leaderboard.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.emptyFlow
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import ru.kpfu.itis.quiz.Res
import ru.kpfu.itis.quiz.core.model.Category
import ru.kpfu.itis.quiz.core.model.Difficulty
import ru.kpfu.itis.quiz.core.model.GameMode
import ru.kpfu.itis.quiz.core.util.toEnumName
import ru.kpfu.itis.quiz.feature.leaderboard.domain.usecase.GetDifficultyUseCase
import ru.kpfu.itis.quiz.feature.leaderboard.domain.usecase.GetGameModeUseCase
import ru.kpfu.itis.quiz.feature.leaderboard.domain.usecase.GetLeaderboardUseCase
import ru.kpfu.itis.quiz.feature.leaderboard.presentation.model.QuestionSettingsUi
import ru.kpfu.itis.quiz.feature.leaderboard.presentation.mvi.LeaderboardScreenIntent
import ru.kpfu.itis.quiz.feature.leaderboard.presentation.mvi.LeaderboardScreenSideEffect
import ru.kpfu.itis.quiz.feature.leaderboard.presentation.mvi.LeaderboardScreenState

class LeaderboardViewModel(
    savedStateHandle: SavedStateHandle,
    private val getGlobalLeaderboardUseCase: GetLeaderboardUseCase,
    private val getGameModeUseCase: GetGameModeUseCase,
    private val getDifficultyUseCase: GetDifficultyUseCase,
): ViewModel(), ContainerHost<LeaderboardScreenState, LeaderboardScreenSideEffect> {

    override val container = container<LeaderboardScreenState, LeaderboardScreenSideEffect>(
        initialState = LeaderboardScreenState(),
        savedStateHandle = savedStateHandle,
        serializer = LeaderboardScreenState.serializer()
    )

    fun onIntent(intent: LeaderboardScreenIntent) {
        when(intent) {
            is LeaderboardScreenIntent.ChangeCategory -> changeCategory(intent)
            is LeaderboardScreenIntent.ChangeDifficulty -> changeDifficulty(intent)
            is LeaderboardScreenIntent.ChangeGameMode -> changeGameMode(intent)
            is LeaderboardScreenIntent.GetLeaderboard -> getLeaderboard(intent)
            LeaderboardScreenIntent.SaveSettings -> saveSettings()
        }
    }

    private fun saveSettings() = intent {
        val settings = QuestionSettingsUi(
            difficulty = state.settings?.difficulty,
            category = state.settings?.category,
            gameMode = state.settings?.gameMode ?: getGameModeUseCase.invoke()
        )
        reduce { state.copy(settings = settings, results = emptyFlow(), loadingEnded = false) }
    }

    private fun getLeaderboard(intent: LeaderboardScreenIntent.GetLeaderboard) = intent {
        if (state.settings == null) sendInitialSettingData().join()
        reduce { state.copy(results = emptyFlow(), loadingEnded = false) }
        try {
            val settings = state.settings
            val resultLeaderboard = getGlobalLeaderboardUseCase.invoke(
                gameMode = settings?.gameMode ?: getGameModeUseCase.invoke(),
                difficulty = settings?.difficulty ?: getDifficultyUseCase.invoke(),
                category = settings?.category,
                limit = intent.limit
            ).flow.cachedIn(viewModelScope)
            reduce { state.copy(results = resultLeaderboard, loadingEnded = true) }
        } catch (ex: Throwable) {
            postSideEffect(LeaderboardScreenSideEffect.ShowError(
                title = Res.string.get_results_fail,
                message = ex.message ?: Res.string.default_error_msg
            ))
        }
    }

    private fun sendInitialSettingData() = intent {
        val settings = QuestionSettingsUi(
            gameMode = getGameModeUseCase.invoke(),
            difficulty = getDifficultyUseCase.invoke(),
            category = null
        )

        reduce { state.copy(settings = settings) }
    }

    private fun changeDifficulty(intent: LeaderboardScreenIntent.ChangeDifficulty) = intent {
        val gameMode = state.settings?.gameMode ?: getGameModeUseCase.invoke()
        if (intent.difficulty == "Any")
            reduce { state.copy(settings = QuestionSettingsUi(
                difficulty = null,
                category = state.settings?.category,
                gameMode = gameMode))
            }
        else {
            reduce { state.copy(settings = QuestionSettingsUi(
                difficulty = Difficulty.valueOf(intent.difficulty.toEnumName()),
                category = state.settings?.category,
                gameMode = gameMode))
            }
        }
    }

    private fun changeCategory(intent: LeaderboardScreenIntent.ChangeCategory) = intent {
        val gameMode = state.settings?.gameMode ?: getGameModeUseCase.invoke()
        if (intent.category == "Any")
            reduce { state.copy(settings = QuestionSettingsUi(
                difficulty = state.settings?.difficulty,
                category = null,
                gameMode = gameMode))
            }
        else {
            reduce { state.copy(settings = QuestionSettingsUi(
                difficulty = state.settings?.difficulty,
                category = Category.valueOf(intent.category.toEnumName()),
                gameMode = gameMode))
            }
        }
    }

    private fun changeGameMode(intent: LeaderboardScreenIntent.ChangeGameMode) = intent {
        reduce { state.copy(settings = QuestionSettingsUi(
            difficulty = state.settings?.difficulty,
            category = state.settings?.category,
            gameMode = GameMode.valueOf(intent.gameMode.toEnumName())))
        }
    }
}

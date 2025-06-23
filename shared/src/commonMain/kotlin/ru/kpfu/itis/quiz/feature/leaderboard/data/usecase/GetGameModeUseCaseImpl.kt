package ru.kpfu.itis.quiz.feature.leaderboard.data.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import ru.kpfu.itis.quiz.core.model.GameMode
import ru.kpfu.itis.quiz.feature.leaderboard.domain.repository.QuestionSettingsRepository
import ru.kpfu.itis.quiz.feature.leaderboard.domain.usecase.GetGameModeUseCase

class GetGameModeUseCaseImpl(
    private val settingsRepository: QuestionSettingsRepository
): GetGameModeUseCase {

    override suspend operator fun invoke(): GameMode {
        return withContext(Dispatchers.IO) {
            settingsRepository.getGameMode()
        }
    }
}
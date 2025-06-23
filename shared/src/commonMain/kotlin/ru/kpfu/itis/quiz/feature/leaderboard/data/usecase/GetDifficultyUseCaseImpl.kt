package ru.kpfu.itis.quiz.feature.leaderboard.data.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import ru.kpfu.itis.quiz.core.model.Difficulty
import ru.kpfu.itis.quiz.feature.leaderboard.domain.repository.QuestionSettingsRepository
import ru.kpfu.itis.quiz.feature.leaderboard.domain.usecase.GetDifficultyUseCase

class GetDifficultyUseCaseImpl(
    private val settingsRepository: QuestionSettingsRepository
): GetDifficultyUseCase {

    override suspend operator fun invoke(): Difficulty {
        return withContext(Dispatchers.IO) {
            settingsRepository.getDifficulty()
        }
    }
}
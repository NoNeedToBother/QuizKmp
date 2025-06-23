package ru.kpfu.itis.quiz.feature.leaderboard.domain.usecase

import ru.kpfu.itis.quiz.core.model.Difficulty

interface GetDifficultyUseCase {
    suspend operator fun invoke(): Difficulty
}

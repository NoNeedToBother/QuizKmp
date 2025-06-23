package ru.kpfu.itis.quiz.feature.leaderboard.domain.usecase

import ru.kpfu.itis.quiz.core.model.GameMode

interface GetGameModeUseCase {
    suspend operator fun invoke(): GameMode
}

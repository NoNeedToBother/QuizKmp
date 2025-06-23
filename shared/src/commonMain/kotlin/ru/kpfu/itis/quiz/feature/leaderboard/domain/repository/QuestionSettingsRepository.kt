package ru.kpfu.itis.quiz.feature.leaderboard.domain.repository

import ru.kpfu.itis.quiz.core.model.Difficulty
import ru.kpfu.itis.quiz.core.model.GameMode

interface QuestionSettingsRepository {

    fun getDifficulty(): Difficulty

    fun getGameMode(): GameMode

}
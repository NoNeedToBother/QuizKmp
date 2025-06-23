package ru.kpfu.itis.quiz.feature.leaderboard.data.repository

import com.russhwolf.settings.Settings
import ru.kpfu.itis.quiz.core.model.Difficulty
import ru.kpfu.itis.quiz.core.model.GameMode
import ru.kpfu.itis.quiz.core.util.toEnumName
import ru.kpfu.itis.quiz.feature.leaderboard.domain.repository.QuestionSettingsRepository

class QuestionSettingsRepositoryImpl(
    private val settings: Settings
) : QuestionSettingsRepository {
    override fun getDifficulty(): Difficulty {
        return getString(DIFFICULTY_KEY)?.let { Difficulty.valueOf(it.toEnumName()) }
            ?: getDefaultDifficulty()
    }

    override fun getGameMode(): GameMode {
        return getString(GAME_MODE_KEY)?.let { GameMode.valueOf(it.toEnumName()) }
            ?: getDefaultGameMode()
    }

    private fun getString(key: String): String? {
        return settings.getStringOrNull(key)
    }

    private fun getDefaultDifficulty(): Difficulty = Difficulty.MEDIUM

    private fun getDefaultGameMode(): GameMode = GameMode.BLITZ

    companion object {
        private const val DIFFICULTY_KEY = "difficulty"
        private const val GAME_MODE_KEY = "game_mode"
    }
}

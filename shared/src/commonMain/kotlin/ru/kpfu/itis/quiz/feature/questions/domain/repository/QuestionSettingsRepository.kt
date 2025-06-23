package ru.kpfu.itis.quiz.feature.questions.domain.repository

import ru.kpfu.itis.quiz.core.model.Category
import ru.kpfu.itis.quiz.core.model.Difficulty
import ru.kpfu.itis.quiz.core.model.GameMode

interface QuestionSettingsRepository {

    fun getDifficulty(): Difficulty

    fun saveDifficulty(difficulty: Difficulty)

    fun getCategory(): Category

    fun saveCategory(category: Category)

    fun getGameMode(): GameMode

    fun saveGameMode(gameMode: GameMode)

}
